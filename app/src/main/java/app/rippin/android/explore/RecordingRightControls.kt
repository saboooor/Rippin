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

@Composable
fun RecordingRightControls(
    isPaused: Boolean,
    onPauseToggle: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.94f),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 7.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Navigation / Location arrow
            Surface(
                shape = CircleShape,
                color = Color(0xFF2C2D33),
                modifier = Modifier.size(42.dp).clickable {}
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.NearMe, "Location", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }

            // 2. Share / Broadcast
            Surface(
                shape = CircleShape,
                color = Color(0xFF0091EA),
                modifier = Modifier.size(42.dp).clickable {}
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.IosShare, "Share", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }

            // 3. SOS / Crash detection
            Surface(
                shape = CircleShape,
                color = Color(0xFFFF3B30),
                modifier = Modifier.size(42.dp).clickable {}
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.LocalHospital, "SOS", tint = Color.White, modifier = Modifier.size(15.dp))
                    Text("SOS", color = Color.White, fontWeight = FontWeight.Black, fontSize = 7.sp)
                }
            }

            // 4. Pause / Resume (Red Circle with Pause icon)
            Surface(
                shape = CircleShape,
                color = Color(0xFFFF453A),
                modifier = Modifier.size(48.dp).clickable { onPauseToggle() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                        "Pause",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // 5. Stop (Yellow Circle with Black Square)
            Surface(
                shape = CircleShape,
                color = Color(0xFFFFD600),
                modifier = Modifier.size(48.dp).clickable { onStop() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Stop,
                        "Stop",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // 6. Rotate / Tilt / Angle
            Surface(
                shape = CircleShape,
                color = Color(0xFF2C2D33),
                modifier = Modifier.size(42.dp).clickable {}
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.CropFree, "Perspective", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }

            // 7. QR Code / Scanner
            Surface(
                shape = CircleShape,
                color = Color.White,
                modifier = Modifier.size(42.dp).clickable {}
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.QrCode, "QR Code", tint = Color.Black, modifier = Modifier.size(20.dp))
                }
            }

            // 8. Poker Spade
            Surface(
                shape = CircleShape,
                color = Color(0xFFFF453A),
                modifier = Modifier.size(42.dp).clickable {}
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("♠", color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
                }
            }
        }
    }
}
