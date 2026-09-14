@file:Suppress("DEPRECATION")

package app.rippin.android.profile

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
fun ProfileScreen(modifier: Modifier) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val profileTabs = listOf(
        "Rides" to Icons.Default.Map,
        "Garage" to Icons.Default.Build,
        "Badges" to Icons.Default.EmojiEvents,
        "Settings" to Icons.Default.Settings
    )

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                                .clickable {},
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Shield, null, tint = Color(0xFFFFB74D), modifier = Modifier.size(16.dp))
                            Text("6", fontWeight = FontWeight.Bold, color = Color(0xFFFFB74D), fontSize = 13.sp)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.QrCode, contentDescription = "QR Code", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(22.dp))
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
            // Header Banner & Overlapping Avatar
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    // Dark banner with red/crimson glow
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0xFF381519), Color(0xFF160E11), Color(0xFF0F0F12))
                                )
                            )
                    )

                    // Avatar overlapping bottom-left of banner
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF222428),
                        border = BorderStroke(2.dp, Color(0xFF40C4FF)),
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.BottomStart)
                            .offset(x = 16.dp, y = 24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🧔🏻‍♂️", fontSize = 38.sp)
                        }
                    }
                }
            }

            // User info: Name, handle, Apple Music button
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Sab", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.White)
                        Text("@sab.oo.r", color = Color(0xFF888888), fontSize = 14.sp)
                    }

                    // Apple Music pill button
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFD62246),
                        modifier = Modifier.clickable {}
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.MusicNote, null, tint = Color.White, modifier = Modifier.size(14.dp))
                            Text("Music", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }

            // Badges Row
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(shape = CircleShape, color = Color(0xFF2A2210), border = BorderStroke(1.dp, Color(0xFFFFD54F).copy(alpha = 0.4f))) {
                        Text("👑 MADE MAN", color = Color(0xFFFFD54F), fontWeight = FontWeight.Bold, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                    Surface(shape = CircleShape, color = Color(0xFF0F2633), border = BorderStroke(1.dp, Color(0xFF40C4FF).copy(alpha = 0.4f))) {
                        Text("🛡 LV 6 · 🐺 ROAD DOG", color = Color(0xFF40C4FF), fontWeight = FontWeight.Bold, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                    Surface(shape = CircleShape, color = Color(0xFF2C1418), border = BorderStroke(1.dp, Color(0xFFE53935).copy(alpha = 0.4f))) {
                        Text("🏍 SCOOTER", color = Color(0xFFEF5350), fontWeight = FontWeight.Bold, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }
            }

            item {
                Surface(shape = CircleShape, color = Color(0xFF2B2513), border = BorderStroke(1.dp, Color(0xFFFFE082).copy(alpha = 0.4f))) {
                    Text("👥 POUTINE LOVERS", color = Color(0xFFFFE082), fontWeight = FontWeight.Bold, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }

            item {
                Text("1 followers  ·  1 following", color = Color(0xFF888888), fontSize = 12.sp)
            }

            // Action buttons: Edit Profile & Instagram handle
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.clickable {}
                    ) {
                        Text("Edit Profile", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp))
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF1E1F24),
                        modifier = Modifier.clickable {}
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.CameraAlt, null, tint = Color.White, modifier = Modifier.size(14.dp))
                            Text("@sab.oo.r", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            // Stats Card (Miles, Rides, Points)
            item {
                Card(
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 18.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("960.6 mi", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                            Text("Miles", color = Color(0xFF888888), fontSize = 12.sp)
                        }
                        Box(Modifier.width(1.dp).height(30.dp).background(Color.White.copy(alpha = 0.08f)))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("29", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                            Text("Rides", color = Color(0xFF888888), fontSize = 12.sp)
                        }
                        Box(Modifier.width(1.dp).height(30.dp).background(Color.White.copy(alpha = 0.08f)))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("580", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                            Text("Points", color = Color(0xFF888888), fontSize = 12.sp)
                        }
                    }
                }
            }

            // Tabs Selector: Rides, Garage, Badges, Settings as Material Design Connected Button Group
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)
                ) {
                    profileTabs.forEachIndexed { index, (label, icon) ->
                        val shapes = when (index) {
                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                            profileTabs.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        }
                        ToggleButton(
                            checked = selectedTab == index,
                            onCheckedChange = { if (it) selectedTab = index },
                            shapes = shapes,
                            modifier = Modifier
                                .weight(1f)
                                .semantics { role = Role.RadioButton },
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(icon, contentDescription = null, modifier = Modifier.size(15.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            // Action Card 1: Invite Riders
            item {
                Card(
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth().clickable {}
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF2C1418),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.DirectionsBike, null, tint = Color(0xFFEF5350), modifier = Modifier.size(20.dp))
                            }
                        }
                        Column(Modifier.padding(start = 14.dp).weight(1f)) {
                            Text("Invite riders", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                            Text("Instagram, text, or link — bring your squad together", color = Color(0xFF888888), fontSize = 12.sp)
                        }
                        Icon(Icons.Default.ChevronRight, null, tint = Color(0xFF888888), modifier = Modifier.size(18.dp))
                    }
                }
            }

            // Action Card 2: Photo Angles NEW
            item {
                Card(
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.fillMaxWidth().clickable {}
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF0E2328),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.CropFree, null, tint = Color(0xFF00E5FF), modifier = Modifier.size(20.dp))
                            }
                        }
                        Column(Modifier.padding(start = 14.dp).weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text("Photo Angles", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                                Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFF00ACC1).copy(alpha = 0.25f)) {
                                    Text("NEW", color = Color(0xFF00E5FF), fontWeight = FontWeight.Bold, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                            Text("Post where you'll be shooting so riders can find you on Explore.", color = Color(0xFF888888), fontSize = 12.sp)
                        }
                        Icon(Icons.Default.ChevronRight, null, tint = Color(0xFF888888), modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}
