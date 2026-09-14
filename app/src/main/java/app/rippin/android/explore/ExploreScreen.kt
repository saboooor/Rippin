@file:Suppress("DEPRECATION")

package app.rippin.android.explore

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
fun ExploreScreen(
    modifier: Modifier = Modifier,
    isRecording: Boolean = false,
    onRecordingChange: (Boolean) -> Unit = {}
) {
    var selectedExploreTab by rememberSaveable { mutableIntStateOf(0) }
    var isPaused by rememberSaveable { mutableStateOf(false) }
    var recordingSeconds by rememberSaveable { mutableIntStateOf(9) }
    var recordingDistance by rememberSaveable { mutableFloatStateOf(0.0f) }

    LaunchedEffect(isRecording, isPaused) {
        if (isRecording) {
            while (isRecording && !isPaused) {
                delay(1000)
                recordingSeconds++
                recordingDistance += 0.01f
            }
        }
    }

    val exploreTabs: List<Pair<String, @Composable () -> Unit>> = listOf(
        "Popular" to { Icon(Icons.Default.LocalFireDepartment, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "All" to { Icon(Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Turf" to { BattleIcon(modifier = Modifier.size(14.dp), tint = LocalContentColor.current) },
        "Segments" to { Icon(Icons.Default.Route, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Local Roads" to { Icon(Icons.Default.AltRoute, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Action" to { Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Meets" to { Icon(Icons.Default.Groups, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Tracks" to { Icon(Icons.Default.TwoWheeler, contentDescription = null, modifier = Modifier.size(15.dp)) },
        "Layers" to { Icon(Icons.Default.Layers, contentDescription = null, modifier = Modifier.size(15.dp)) },
    )

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            MapSurface(Modifier.fillMaxSize())

            // FULLSCREEN RECORDING MODE
            if (isRecording) {
                // 1. Center Map Marker with "Return here"
                RecordingCenterMarker(
                    modifier = Modifier.align(Alignment.Center)
                )

                // 2. Top-Left "▲ Recording" Pill
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.9f),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .statusBarsPadding()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.NearMe, null, tint = Color(0xFFFF3B30), modifier = Modifier.size(15.dp))
                        Text("Recording", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }

                // 3. Right-Side Vertical Floating Controls
                RecordingRightControls(
                    isPaused = isPaused,
                    onPauseToggle = { isPaused = !isPaused },
                    onStop = { onRecordingChange(false) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp, bottom = 120.dp)
                )

                // 4. Bottom Recording Deck (Stats & Media Controls)
                RecordingBottomDeck(
                    distanceMi = recordingDistance,
                    durationSec = recordingSeconds,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            } else {
                // NORMAL EXPLORE VIEW
                // Top Quick Action Filter: Poker (Top Left)
                FilledTonalButton(
                    onClick = {},
                    shape = CircleShape,
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .statusBarsPadding()
                        .padding(16.dp)
                ) {
                    Text("♠", fontSize = 16.sp, fontWeight = FontWeight.Black)
                    Spacer(Modifier.width(6.dp))
                    Text("Poker", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }

                // Top Quick Action Filter: Turf (Top Right)
                FilledTonalButton(
                    onClick = {},
                    shape = CircleShape,
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .statusBarsPadding()
                        .padding(16.dp)
                ) {
                    BattleIcon(modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    Spacer(Modifier.width(6.dp))
                    Text("Turf", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }

                // Expressive Action Buttons (Location & Recording)
                Column(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.End,
                ) {
                    FloatingActionButton(
                        onClick = {},
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Icon(Icons.Default.MyLocation, "Current location")
                    }
                    FloatingActionButton(
                        onClick = { onRecordingChange(true) },
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = MaterialTheme.shapes.large,
                    ) {
                        Text(
                            "REC",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Bottom Section: Floating Button Group above the Bottom Sheet
                Column(
                    Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Native Material 3 Expressive Connected Button Group Floating Above the Sheet
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                    ) {
                        exploreTabs.forEachIndexed { index, (label, icon) ->
                            val shapes = when (index) {
                                0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                exploreTabs.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                            }
                            ToggleButton(
                                checked = selectedExploreTab == index,
                                onCheckedChange = { if (it) selectedExploreTab = index },
                                shapes = shapes,
                                modifier = Modifier.semantics { role = Role.RadioButton },
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                ) {
                                    icon()
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = if (selectedExploreTab == index) FontWeight.Bold else FontWeight.Normal,
                                        maxLines = 1,
                                    )
                                }
                            }
                        }
                    }

                    // Bottom Content Sheet
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        modifier = Modifier.fillMaxWidth(),
                        tonalElevation = 3.dp
                    ) {
                        Column(
                            Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ElevatedCard(
                                colors = CardDefaults.elevatedCardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                                ),
                                shape = MaterialTheme.shapes.large,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.primary)
                                        Text(
                                            "Where to?",
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(start = 10.dp)
                                        )
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Route, null, tint = MaterialTheme.colorScheme.primary)
                                        Text(
                                            "Recent routes",
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.weight(1f).padding(start = 10.dp)
                                        )
                                        Text(
                                            "Kingston & Church",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                            MediaPlayer()
                        }
                    }
                }
            }
        }
    }
}

internal const val DARK_MAP_STYLE = """[
  {"elementType":"geometry","stylers":[{"color":"#121820"}]},
  {"elementType":"labels.text.fill","stylers":[{"color":"#74859a"}]},
  {"elementType":"labels.text.stroke","stylers":[{"color":"#121820"}]},
  {"featureType":"administrative.locality","elementType":"labels.text.fill","stylers":[{"color":"#d4dfec"}]},
  {"featureType":"poi","stylers":[{"visibility":"off"}]},
  {"featureType":"road","elementType":"geometry","stylers":[{"color":"#1f2a37"}]},
  {"featureType":"road","elementType":"geometry.stroke","stylers":[{"color":"#16202c"}]},
  {"featureType":"road.highway","elementType":"geometry","stylers":[{"color":"#2d3f53"}]},
  {"featureType":"transit","stylers":[{"visibility":"off"}]},
  {"featureType":"water","elementType":"geometry","stylers":[{"color":"#0b1117"}]}
]"""
