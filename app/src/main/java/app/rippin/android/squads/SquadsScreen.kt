@file:Suppress("DEPRECATION")

package app.rippin.android.squads

import app.rippin.android.*
import app.rippin.android.navigation.*
import app.rippin.android.components.*
import app.rippin.android.home.*
import app.rippin.android.explore.*
import app.rippin.android.squads.*
import app.rippin.android.community.*
import app.rippin.android.profile.*


import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.service.notification.NotificationListenerService
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SquadsScreen(modifier: Modifier) {
    var selectedSquadTab by rememberSaveable { mutableIntStateOf(0) }
    var selectedSubTab by rememberSaveable { mutableIntStateOf(0) }
    val squadTabs: List<Pair<String, @Composable () -> Unit>> = listOf(
        "Feed" to { Icon(Icons.Default.DynamicFeed, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Stats" to { Icon(Icons.Default.BarChart, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Members" to { Icon(Icons.Default.Groups, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Poker" to { Text("♠", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = LocalContentColor.current) },
    )
    val subTabs: List<Pair<String, @Composable () -> Unit>> = listOf(
        "Posts" to { Icon(Icons.Default.Article, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Rides" to { Icon(Icons.Default.TwoWheeler, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Photos 6" to { Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(15.dp)) },
    )

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF2E1719),
                        border = BorderStroke(1.dp, Color(0xFFE53935).copy(alpha = 0.4f)),
                        modifier = Modifier.clickable {}
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Search, null, tint = Color(0xFFE53935), modifier = Modifier.size(16.dp))
                            Text("Find", color = Color(0xFFE53935), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                },
                actions = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.PersonAdd, contentDescription = "Add Person", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = CircleShape,
                containerColor = Color(0xFF29B6F6),
                contentColor = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Squad Chat", modifier = Modifier.size(24.dp))
            }
        }
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Featured & Poker Run Cards
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Card 1: JB Coffee Run
                    Card(
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFFFBE9E7),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("JB", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("FEATURED", color = Color(0xFFE53935), fontWeight = FontWeight.Bold, fontSize = 10.sp)
                                    Icon(Icons.Default.ChevronRight, null, tint = Color(0xFFE53935), modifier = Modifier.size(14.dp))
                                }
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("JB Moto Coffee Run", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                                Text("Passport & rewards", color = Color(0xFF888888), fontSize = 11.sp)
                            }
                        }
                    }

                    // Card 2: Poker Run
                    Card(
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFF221618),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("♠", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("POKER RUN", color = Color(0xFFE53935), fontWeight = FontWeight.Bold, fontSize = 10.sp)
                                    Icon(Icons.Default.ChevronRight, null, tint = Color(0xFFE53935), modifier = Modifier.size(14.dp))
                                }
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("Rippin' Poker Run", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                                Text("Draw a card anywhere", color = Color(0xFF888888), fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // Squad Banner
            item {
                Card(
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF4A3423),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🥔", fontSize = 20.sp)
                            }
                        }
                        Column(Modifier.padding(start = 12.dp).weight(1f)) {
                            Text("Poutine lovers", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                            Text("1 riders", fontSize = 12.sp, color = Color(0xFF888888))
                        }
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF1B3825),
                            border = BorderStroke(1.dp, Color(0xFF00E676).copy(alpha = 0.4f)),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("LIVE", color = Color(0xFF00E676), fontWeight = FontWeight.Bold, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }
                        Icon(Icons.Default.KeyboardArrowDown, null, tint = Color(0xFF888888), modifier = Modifier.size(20.dp))
                    }
                }
            }

            // Squad Bio Header
            item {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(76.dp)) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF3E2C1E),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🍟", fontSize = 34.sp)
                            }
                        }
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFE53935),
                            modifier = Modifier.size(26.dp).align(Alignment.BottomEnd)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.CameraAlt, null, tint = Color.White, modifier = Modifier.size(14.dp))
                            }
                        }
                    }

                    Column(Modifier.padding(start = 16.dp)) {
                        Text("1 members · 940.1 mi · Public", color = Color(0xFF888888), fontSize = 13.sp)
                        Spacer(Modifier.height(2.dp))
                        Text("the squad for canadian people", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                    }
                }
            }

            // Group Ride Live Card
            item {
                Card(
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFF00E676).copy(alpha = 0.15f),
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Groups, null, tint = Color(0xFF00E676), modifier = Modifier.size(20.dp))
                                    }
                                }
                                Column {
                                    Text("Group Ride Live", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                                    Text("0 riders sharing now.", fontSize = 12.sp, color = Color(0xFF888888))
                                }
                            }

                            Surface(
                                shape = CircleShape,
                                color = Color(0xFF00E676),
                                modifier = Modifier.clickable {}
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.Send, null, tint = Color.Black, modifier = Modifier.size(14.dp))
                                    Text("Join Group Ride", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF132219),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Search, null, tint = Color(0xFF00E676).copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                                Text("Waiting for riders to share location.", color = Color(0xFF888888), fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // Squad Navigation Tabs: Feed, Stats, Members, Poker (Material 3 Button Group)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                ) {
                    squadTabs.forEachIndexed { index, (tab, icon) ->
                        val shapes = when (index) {
                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                            squadTabs.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        }
                        ToggleButton(
                            checked = selectedSquadTab == index,
                            onCheckedChange = { if (it) selectedSquadTab = index },
                            shapes = shapes,
                            modifier = Modifier.weight(1f).semantics { role = Role.RadioButton },
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                icon()
                                Text(
                                    text = tab,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (selectedSquadTab == index) FontWeight.Bold else FontWeight.Normal,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }
            }

            // Red Live Bar: "1 riding now >"
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF261316),
                    border = BorderStroke(1.dp, Color(0xFFE53935).copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth().clickable {}
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE53935))
                            )
                            Text("1 riding now", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                        Icon(Icons.Default.ChevronRight, null, tint = Color(0xFFE53935), modifier = Modifier.size(16.dp))
                    }
                }
            }

            // Sub-filters: Posts, Rides, Photos 6 (Material 3 Button Group)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                ) {
                    subTabs.forEachIndexed { index, (tab, icon) ->
                        val shapes = when (index) {
                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                            subTabs.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        }
                        ToggleButton(
                            checked = selectedSubTab == index,
                            onCheckedChange = { if (it) selectedSubTab = index },
                            shapes = shapes,
                            modifier = Modifier.weight(1f).semantics { role = Role.RadioButton },
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                icon()
                                Text(
                                    text = tab,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (selectedSubTab == index) FontWeight.Bold else FontWeight.Normal,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }
            }

            // Post Composer Row
            item {
                WhatIsHappeningComposer()
            }

            item {
                Text(
                    "Squad posts",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
    }
}
