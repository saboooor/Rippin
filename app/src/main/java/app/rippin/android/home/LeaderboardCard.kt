@file:Suppress("DEPRECATION")

package app.rippin.android.home

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

@Composable
fun LeaderboardCard(crownOffset: Float) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = LeaderboardContainer
        ),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, LeaderboardAccent.copy(alpha = 0.30f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            Modifier.size(44.dp),
                            CircleShape,
                            color = Color(0xFFFF5252).copy(alpha = 0.22f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.EmojiEvents,
                                    null,
                                    tint = LeaderboardAccent,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Column(Modifier.padding(start = 12.dp)) {
                            Text(
                                "Leaderboard",
                                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "Chase the top riders",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = Color(0xFFB0B0B0)
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF2B1316),
                        border = BorderStroke(1.dp, LeaderboardAccent.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("#1", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(Modifier.width(3.dp))
                            Icon(Icons.Default.ChevronRight, null, tint = LeaderboardAccent, modifier = Modifier.size(14.dp))
                        }
                    }
                }

                // POINTS Row
                LeaderboardPodiumRow(
                    label = "POINTS",
                    labelColor = Color(0xFFFF5252),
                    p1Name = "@trigger_trav21",
                    p2Name = "@vampnwolf",
                    p3Name = "@ty.7fitty",
                    containerBg = Color(0xFF1B0F12),
                    borderColor = Color(0xFFFF6B70).copy(alpha = 0.28f),
                    crownOffset = crownOffset
                )

                // XP Row
                LeaderboardPodiumRow(
                    label = "XP",
                    labelColor = Color(0xFF40C4FF),
                    p1Name = "@trigger_trav21",
                    p2Name = "@ty.7fitty",
                    p3Name = "@twonsmbs",
                    containerBg = Color(0xFF0F141B),
                    borderColor = Color(0xFF40C4FF).copy(alpha = 0.25f),
                    crownOffset = crownOffset
                )
            }

            // Checkered Racing Flag banner strip across the bottom
            Canvas(
                Modifier
                    .fillMaxWidth()
                    .height(14.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                val squareSize = 8.dp.toPx()
                val cols = (size.width / squareSize).toInt() + 2
                for (r in 0..1) {
                    for (c in 0 until cols) {
                        val isWhite = (r + c) % 2 == 0
                        drawRect(
                            color = if (isWhite) Color.White else Color(0xFF1E1C1F),
                            topLeft = Offset(c * squareSize, r * squareSize),
                            size = Size(squareSize, squareSize)
                        )
                    }
                }
            }
        }
    }
}
