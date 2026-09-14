@file:Suppress("DEPRECATION")

package app.rippin.android.community

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
fun CommunityScreen(modifier: Modifier) {
    var selectedFilter by rememberSaveable { mutableIntStateOf(0) }
    var highlightsExpanded by rememberSaveable { mutableStateOf(true) }
    val filters = listOf(
        "Status" to Icons.Default.ChatBubbleOutline,
        "Rides" to Icons.Default.TwoWheeler,
        "Events" to Icons.Default.CalendarMonth,
    )

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}, modifier = Modifier.padding(start = 4.dp)) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(22.dp))
                    }
                },
                title = {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Community", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 18.sp)
                    }
                },
                actions = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Shield, contentDescription = "Shield", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(20.dp))
                        }
                        IconButton(onClick = {}) {
                            BadgedBox(
                                badge = {
                                    Badge(containerColor = MaterialTheme.colorScheme.error, contentColor = MaterialTheme.colorScheme.onError) {
                                        Text("9+", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(22.dp))
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { WhatIsHappeningComposer() }

            // Rider Highlights Card
            item {
                Card(
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.EmojiEvents, null, tint = Color(0xFFE53935), modifier = Modifier.size(18.dp))
                                Text("RIDER HIGHLIGHTS", color = Color(0xFFE53935), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.08f),
                                modifier = Modifier.size(28.dp).clickable { highlightsExpanded = !highlightsExpanded }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        if (highlightsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        if (highlightsExpanded) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // Card 1
                                Card(
                                    shape = MaterialTheme.shapes.medium,
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Surface(shape = CircleShape, color = Color(0xFF4A3423), modifier = Modifier.size(28.dp)) {
                                            Box(contentAlignment = Alignment.Center) { Text("🏍", fontSize = 12.sp) }
                                        }
                                        Text("Rider of the Day", color = Color(0xFF888888), fontSize = 10.sp)
                                        Text("@unquiet_mindx", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White, maxLines = 1)
                                        Text("16 mi", color = Color(0xFF888888), fontSize = 11.sp)
                                    }
                                }

                                // Card 2
                                Card(
                                    shape = MaterialTheme.shapes.medium,
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Surface(shape = CircleShape, color = Color(0xFF333333), modifier = Modifier.size(28.dp)) {
                                            Box(contentAlignment = Alignment.Center) { Text("👥", fontSize = 12.sp) }
                                        }
                                        Text("This Week", color = Color(0xFF888888), fontSize = 10.sp)
                                        Text("Week's best", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White, maxLines = 1)
                                        Text("5 awards", color = Color(0xFF888888), fontSize = 11.sp)
                                    }
                                }

                                // Card 3 (You)
                                Card(
                                    shape = MaterialTheme.shapes.medium,
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Icon(Icons.Default.DirectionsBike, null, tint = Color(0xFFE53935), modifier = Modifier.size(20.dp))
                                        Text("You", color = Color(0xFFE53935), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                        Text("6 mi", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                                        Text("2 rides · #108", color = Color(0xFF888888), fontSize = 10.sp, maxLines = 1)
                                    }
                                }
                            }
                        }

                        // Your Week sub-section
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.CalendarToday, null, tint = Color(0xFFE53935), modifier = Modifier.size(14.dp))
                                    Text("YOUR WEEK", color = Color(0xFFE53935), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                }
                                Icon(Icons.Default.Share, null, tint = Color(0xFF888888), modifier = Modifier.size(16.dp))
                            }

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                WeekStatBox(Icons.Default.Flag, "6", "miles")
                                WeekStatBox(Icons.Default.Route, "2", "rides")
                                WeekStatBox(Icons.Default.Map, "0", "sq mi")
                                WeekStatBox(Icons.Default.Timer, "0", "PBs")
                            }

                            Text(
                                "You're #108 of 108 riders in miles this week.",
                                color = Color(0xFF888888),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // Filter Pills (Material 3 Button Group)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                ) {
                    filters.forEachIndexed { index, (filter, icon) ->
                        val shapes = when (index) {
                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                            filters.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        }
                        ToggleButton(
                            checked = selectedFilter == index,
                            onCheckedChange = { if (it) selectedFilter = index },
                            shapes = shapes,
                            modifier = Modifier.weight(1f).semantics { role = Role.RadioButton },
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Icon(icon, contentDescription = null, modifier = Modifier.size(15.dp))
                                Text(
                                    text = filter,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (selectedFilter == index) FontWeight.Bold else FontWeight.Normal,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }
            }

            // Feed Post: @xbhobx
            item {
                Card(
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(shape = CircleShape, color = Color(0xFF243242), modifier = Modifier.size(42.dp)) {
                                Box(contentAlignment = Alignment.Center) { Text("🏍", fontSize = 20.sp) }
                            }
                            Column(Modifier.padding(start = 12.dp).weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("@xbhobx", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                                    Icon(Icons.Default.Verified, null, tint = Color(0xFF29B6F6), modifier = Modifier.size(14.dp))
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.padding(top = 2.dp)
                                ) {
                                    Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFF3E2C1E)) {
                                        Text("🛡 Iron Rider", color = Color(0xFFFFB74D), fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                    }
                                    Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFF3E321E)) {
                                        Text("👑 TURF", color = Color(0xFFFFD54F), fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                    }
                                    Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFF222428)) {
                                        Text("🏍 Street Triple RS", color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                    }
                                }
                            }
                            Text("👑", fontSize = 16.sp)
                        }

                        Text("TURF · 60.0 mi loop", color = Color(0xFFFFD54F), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("Claimed 174 sq mi of Turf", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                        Text("Made Man territory is live on Explore.", color = Color(0xFF888888), fontSize = 13.sp)

                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF0F1E28))
                        ) {
                            RouteMapPreview()
                            Row(
                                Modifier.align(Alignment.BottomStart).padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(shape = RoundedCornerShape(4.dp), color = Color.Black.copy(alpha = 0.7f)) {
                                    Text("👑 Made Man", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                                Surface(shape = RoundedCornerShape(4.dp), color = Color.Black.copy(alpha = 0.7f)) {
                                    Text("174 sq mi", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
