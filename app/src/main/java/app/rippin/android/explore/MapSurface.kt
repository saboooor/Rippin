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
fun MapSurface(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val apiKey = remember(context) {
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            appInfo.metaData?.getString("com.google.android.geo.API_KEY")?.takeIf { it.isNotBlank() }
        } catch (_: Exception) {
            null
        }
    }

    Box(modifier = modifier.fillMaxSize().background(Color(0xFF0F141C))) {
        if (!apiKey.isNullOrBlank()) {
            val carlsbad = remember { LatLng(33.1581, -117.3506) }
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(carlsbad, 12.5f)
            }
            val uiSettings = remember {
                MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = false,
                    compassEnabled = false,
                    mapToolbarEnabled = false,
                    rotationGesturesEnabled = true,
                    scrollGesturesEnabled = true,
                    tiltGesturesEnabled = true,
                    zoomGesturesEnabled = true
                )
            }
            val mapProperties = remember {
                MapProperties(
                    mapStyleOptions = MapStyleOptions(DARK_MAP_STYLE)
                )
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                uiSettings = uiSettings
            )
        } else {
            // Dark Mode Map Surface (matching DARK_MAP_STYLE dark palette when live API key is not configured)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Dark water / ocean geometry on west
                drawRect(
                    color = Color(0xFF0B1117),
                    topLeft = Offset(0f, 0f),
                    size = Size(w * 0.26f, h)
                )

                // Coastline stroke
                drawLine(
                    color = Color(0xFF16202C),
                    start = Offset(w * 0.26f, 0f),
                    end = Offset(w * 0.26f, h),
                    strokeWidth = 3f
                )

                // Main Highway
                val path1 = androidx.compose.ui.graphics.Path().apply {
                    moveTo(w * 0.30f, 0f)
                    cubicTo(w * 0.33f, h * 0.35f, w * 0.28f, h * 0.65f, w * 0.32f, h)
                }
                drawPath(path1, color = Color(0xFF2D3F53), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 6f))

                // East-West Arterial Route
                val path2 = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, h * 0.42f)
                    cubicTo(w * 0.35f, h * 0.40f, w * 0.65f, h * 0.45f, w, h * 0.38f)
                }
                drawPath(path2, color = Color(0xFF2D3F53), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5f))

                // Grid of Secondary Roads
                val roads = listOf(
                    Offset(w * 0.26f, h * 0.20f) to Offset(w, h * 0.25f),
                    Offset(w * 0.26f, h * 0.58f) to Offset(w, h * 0.55f),
                    Offset(w * 0.52f, 0f) to Offset(w * 0.50f, h),
                    Offset(w * 0.72f, 0f) to Offset(w * 0.74f, h),
                    Offset(w * 0.35f, h * 0.12f) to Offset(w * 0.85f, h * 0.82f)
                )
                roads.forEach { (start, end) ->
                    drawLine(
                        color = Color(0xFF1F2A37),
                        start = start,
                        end = end,
                        strokeWidth = 3f
                    )
                }

                // Turf Area Highlight
                drawCircle(
                    color = Color(0xFFFFD54F).copy(alpha = 0.08f),
                    radius = 180f,
                    center = Offset(w * 0.48f, h * 0.42f)
                )
                drawCircle(
                    color = Color(0xFFFFD54F).copy(alpha = 0.35f),
                    radius = 180f,
                    center = Offset(w * 0.48f, h * 0.42f),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = 2f,
                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(12f, 8f))
                    )
                )
            }
        }
    }
}
