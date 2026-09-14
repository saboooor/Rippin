@file:Suppress("DEPRECATION")

package app.rippin.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CropFree
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.service.notification.NotificationListenerService
import androidx.compose.ui.platform.LocalContext

private val RideReportContainer = Color(0xFF0C130E)
private val RideReportAccent = Color(0xFF55D889)
private val ShopContainer = Color(0xFF13110C)
private val ShopAccent = Color(0xFFFFD21F)
private val LeaderboardContainer = Color(0xFF150D0F)
private val LeaderboardAccent = Color(0xFFFF6B70)
private val SegmentsContainer = Color(0xFF111317)
private val SegmentsAccent = Color(0xFFE2E4E8)

private enum class Destination(val label: String, val icon: ImageVector) {
    Home("Home", Icons.Default.Home),
    Explore("Explore", Icons.Default.Map),
    Squads("Squads", Icons.Default.Groups),
    Community("Community", Icons.Default.Article),
    Profile("Profile", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
        try {
            NotificationListenerService.requestRebind(ComponentName(this, MediaAccessService::class.java))
        } catch (_: Exception) {}

        setContent { RippinTheme { RippinApp() } }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun RippinApp() {
    var selectedName by rememberSaveable { mutableStateOf(Destination.Home.name) }
    val selected = Destination.valueOf(selectedName)

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            ShortNavigationBar {
                Destination.entries.forEach { item ->
                    ShortNavigationBarItem(
                        selected = item == selected,
                        onClick = { selectedName = item.name },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                    )
                }
            }
        }
    ) { padding ->
        when (selected) {
            Destination.Home -> HomeScreen(Modifier.padding(padding))
            Destination.Explore -> ExploreScreen(Modifier.padding(padding))
            Destination.Squads -> SquadsScreen(Modifier.padding(padding))
            Destination.Community -> CommunityScreen(Modifier.padding(padding))
            Destination.Profile -> ProfileScreen(Modifier.padding(padding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(modifier: Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "homeAnimations")
    val crownOffset by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "crownOffset"
    )
    val starAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "starAlpha"
    )
    val pulseGlow by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.65f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseGlow"
    )

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { Text("Home", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface) },
                actions = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
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
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError
                                    ) {
                                        Text("9+", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(22.dp))
                            }
                        }

                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
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
            item { StatusCard(pulseGlow) }
            item { PointsShopCard(starAlpha) }
            item { LeaderboardCard(crownOffset) }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SegmentsStatCard(Modifier.weight(1f))
                    UpcomingMeetsStatCard(Modifier.weight(1f))
                }
            }
            item { MotoEventsCard() }
            item { RiderTipsCard() }
            item { UpcomingRidesSection() }
        }
    }
}

@Composable
private fun StatusCard(pulseGlow: Float) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = RideReportContainer
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, RideReportAccent.copy(alpha = pulseGlow)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.08f),
                shape = CircleShape,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("🏍️", style = MaterialTheme.typography.titleMedium)
                }
            }
            Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(
                    "WEATHER + RIDE REPORT",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = RideReportAccent,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Daily Ride Report",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Weather: 65° · Clear · wind 9 mph",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = Color(0xFF9E9E9E)
                )
                Text(
                    "Check road alerts and closure notes before you roll. Nearby route intel ne...",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = Color(0xFF7E7E7E),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "87",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                        color = RideReportAccent,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        "SCORE",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = RideReportAccent,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(8.dp))
                Surface(
                    color = Color.White.copy(alpha = 0.06f),
                    shape = CircleShape,
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.ChevronRight,
                            "Expand ride report",
                            tint = RideReportAccent,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PointsShopCard(starAlpha: Float) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = ShopContainer
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, ShopAccent.copy(alpha = 0.28f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(Modifier.fillMaxWidth()) {
            Canvas(Modifier.matchParentSize()) {
                val starColor = ShopAccent.copy(alpha = starAlpha * 0.35f)
                val starColor2 = ShopAccent.copy(alpha = (1f - starAlpha) * 0.25f + 0.1f)
                val cx1 = size.width * 0.78f
                val cy1 = size.height * 0.42f
                val r1 = 18.dp.toPx()
                drawLine(starColor, Offset(cx1 - r1, cy1), Offset(cx1 + r1, cy1), strokeWidth = 1.8f)
                drawLine(starColor, Offset(cx1, cy1 - r1), Offset(cx1, cy1 + r1), strokeWidth = 1.8f)
                drawCircle(starColor, radius = 3.dp.toPx(), center = Offset(cx1, cy1))

                val cx2 = size.width * 0.88f
                val cy2 = size.height * 0.62f
                val r2 = 12.dp.toPx()
                drawLine(starColor2, Offset(cx2 - r2, cy2), Offset(cx2 + r2, cy2), strokeWidth = 1.5f)
                drawLine(starColor2, Offset(cx2, cy2 - r2), Offset(cx2, cy2 + r2), strokeWidth = 1.5f)
                drawCircle(starColor2, radius = 2.dp.toPx(), center = Offset(cx2, cy2))
            }

            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    Modifier.size(46.dp),
                    CircleShape,
                    color = ShopAccent.copy(alpha = 0.16f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.CardGiftcard, null, tint = ShopAccent, modifier = Modifier.size(24.dp))
                    }
                }
                Column(Modifier.padding(start = 14.dp).weight(1f)) {
                    Text(
                        "Points Shop",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "Redeem gear & perks",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = Color(0xFF9E9E9E)
                    )
                }
                Surface(
                    shape = CircleShape,
                    color = ShopAccent
                ) {
                    Text(
                        "580 pts",
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LeaderboardCard(crownOffset: Float) {
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

@Composable
private fun LeaderboardPodiumRow(
    label: String,
    labelColor: Color,
    p1Name: String,
    p2Name: String,
    p3Name: String,
    containerBg: Color,
    borderColor: Color,
    crownOffset: Float
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = containerBg,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                label,
                color = labelColor,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                modifier = Modifier.width(56.dp)
            )

            Row(
                Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                PodiumRiderItem(
                    rank = "P2",
                    username = p2Name,
                    accentColor = Color(0xFFFF5252),
                    avatarEmoji = "🏍️",
                    isP1 = false,
                    crownOffset = 0f
                )

                PodiumRiderItem(
                    rank = "P1",
                    username = p1Name,
                    accentColor = Color(0xFFFFD21F),
                    avatarEmoji = "👑",
                    isP1 = true,
                    crownOffset = crownOffset
                )

                PodiumRiderItem(
                    rank = "P3",
                    username = p3Name,
                    accentColor = Color(0xFFFF8A65),
                    avatarEmoji = "🏍️",
                    isP1 = false,
                    crownOffset = 0f
                )
            }
        }
    }
}

@Composable
private fun PodiumRiderItem(
    rank: String,
    username: String,
    accentColor: Color,
    avatarEmoji: String,
    isP1: Boolean,
    crownOffset: Float
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        if (isP1) {
            Text(
                "👑",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .offset(y = crownOffset.dp)
                    .padding(bottom = 1.dp)
            )
        } else {
            Spacer(Modifier.height(20.dp))
        }

        Surface(
            shape = CircleShape,
            border = BorderStroke(if (isP1) 2.5.dp else 1.5.dp, accentColor),
            color = Color(0xFF221518),
            modifier = Modifier.size(if (isP1) 46.dp else 38.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    if (isP1) "🏍️" else "👤",
                    style = if (isP1) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.labelSmall
                )
            }
        }

        Spacer(Modifier.height(3.dp))
        Text(
            rank,
            color = accentColor,
            fontWeight = FontWeight.ExtraBold,
            style = if (isP1) MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp) else MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
        )
        Text(
            username,
            color = Color.White.copy(alpha = 0.90f),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun SegmentsStatCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = SegmentsContainer
        ),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.12f))
    ) {
        Box(Modifier.fillMaxWidth()) {
            Canvas(Modifier.matchParentSize()) {
                val squareSize = 20.dp.toPx()
                val cols = 5
                val rows = 4
                val startX = size.width - cols * squareSize
                val startY = size.height - rows * squareSize
                for (r in 0 until rows) {
                    for (c in 0 until cols) {
                        if ((r + c) % 2 == 0) {
                            val alpha = ((c.toFloat() / cols) * (r.toFloat() / rows) * 0.14f).coerceIn(0f, 0.16f)
                            drawRect(
                                color = Color.White.copy(alpha = alpha),
                                topLeft = Offset(startX + c * squareSize, startY + r * squareSize),
                                size = Size(squareSize, squareSize)
                            )
                        }
                    }
                }
            }

            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.12f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Flag, null, tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                    Column(Modifier.padding(start = 10.dp)) {
                        Text(
                            "Segments",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "Near you",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = Color(0xFF888888)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        "299",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "nearby",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = Color(0xFF888888),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }

                Text(
                    "Chase the leaderboard",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = Color(0xFF888888)
                )
            }
        }
    }
}

@Composable
private fun UpcomingMeetsStatCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0D1611)
        ),
        border = BorderStroke(1.dp, RideReportAccent.copy(alpha = 0.25f))
    ) {
        Box(Modifier.fillMaxWidth()) {
            Box(
                Modifier
                    .matchParentSize()
                    .background(
                        Brush.radialGradient(
                            listOf(RideReportAccent.copy(alpha = 0.18f), Color.Transparent),
                            center = Offset(300f, 300f),
                            radius = 280f
                        )
                    )
            )

            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = RideReportAccent.copy(alpha = 0.18f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.CalendarToday, null, tint = RideReportAccent, modifier = Modifier.size(17.dp))
                        }
                    }
                    Column(Modifier.padding(start = 10.dp)) {
                        Text(
                            "Upcoming Meets",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "Community",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = Color(0xFF888888)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        "2",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "soon",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = Color(0xFF888888),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }

                Text(
                    "Pull up & meet the crew",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = RideReportAccent.copy(alpha = 0.70f)
                )
            }
        }
    }
}

@Composable
private fun MotoEventsCard() {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0C1929),
            contentColor = Color.White
        ),
        border = BorderStroke(1.dp, Color(0xFF40C4FF).copy(alpha = 0.28f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(Modifier.fillMaxWidth()) {
            Canvas(
                Modifier
                    .size(180.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 30.dp, y = 10.dp)
            ) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2.2f
                val strokeColor = Color(0xFF40C4FF).copy(alpha = 0.08f)
                val fillShape = Color(0xFF40C4FF).copy(alpha = 0.04f)
                drawCircle(fillShape, radius = radius, center = center)
                drawCircle(strokeColor, radius = radius, center = center, style = androidx.compose.ui.graphics.drawscope.Stroke(1.5f))
                drawOval(strokeColor, topLeft = Offset(center.x - radius * 0.45f, center.y - radius), size = Size(radius * 0.9f, radius * 2), style = androidx.compose.ui.graphics.drawscope.Stroke(1.5f))
                drawOval(strokeColor, topLeft = Offset(center.x - radius, center.y - radius * 0.45f), size = Size(radius * 2, radius * 0.9f), style = androidx.compose.ui.graphics.drawscope.Stroke(1.5f))
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        Modifier.size(38.dp),
                        CircleShape,
                        color = Color(0xFF40C4FF).copy(alpha = 0.16f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Public, null, tint = Color(0xFF40C4FF), modifier = Modifier.size(20.dp))
                        }
                    }
                    Column(Modifier.padding(start = 12.dp)) {
                        Text(
                            "Moto Events",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "Big calendar",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = Color(0xFF888888)
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        "MotoGP",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 22.sp),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "DGR · rallies · bike nights",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = Color(0xFF888888)
                    )
                }
            }
        }
    }
}

@Composable
private fun RiderTipsCard() {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Lightbulb,
                        null,
                        tint = Color(0xFFE53935),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        "Rider Tips",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFFE53935)
                    )
                }
                Text(
                    "More",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color(0xFFE53935)
                )
            }

            // Tip 1
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    Icons.Default.Sensors,
                    null,
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(20.dp).padding(top = 2.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        "Just ride — we do the rest",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        "Hit Record and Rippin auto-times segments, tracks your turf, and collects poker-run cards while you ride.",
                        fontSize = 12.sp,
                        color = Color(0xFF9E9E9E),
                        lineHeight = 16.sp
                    )
                }
            }

            // Tip 2
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    Icons.Default.Map,
                    null,
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(20.dp).padding(top = 2.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        "Explore tab — the map",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        "Find segments, open turf, poker runs, and meets on the roads near you.",
                        fontSize = 12.sp,
                        color = Color(0xFF9E9E9E),
                        lineHeight = 16.sp
                    )
                }
            }

            // Tip 3
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    Icons.Default.Flag,
                    null,
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(20.dp).padding(top = 2.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        "Segments time themselves",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        "Ride through both gates while recording and your time saves and hits the board automatically.",
                        fontSize = 12.sp,
                        color = Color(0xFF9E9E9E),
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun UpcomingRidesSection() {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Upcoming Rides",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF422124),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("F", fontWeight = FontWeight.Bold, color = Color(0xFFEF5350), fontSize = 16.sp)
                        }
                    }
                    Column(Modifier.padding(start = 12.dp).weight(1f)) {
                        Text("feralphantom72", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                        Text("3 days, 3 hr", fontSize = 12.sp, color = Color(0xFF888888))
                    }
                    Icon(
                        Icons.Default.Share,
                        null,
                        tint = Color(0xFF888888),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0F1E28))
                ) {
                    RouteMapPreview()
                }

                Row(
                    Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(Icons.Default.NearMe, null, tint = Color(0xFFE53935), modifier = Modifier.size(16.dp))
                    Text("Euharlee, GA", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun RouteMapPreview(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(Color(0xFF0F1E28))
        val waterColor = Color(0xFF0B161E)
        drawCircle(waterColor, radius = 90.dp.toPx(), center = Offset(size.width * 0.65f, size.height * 0.5f))
        drawCircle(waterColor, radius = 50.dp.toPx(), center = Offset(size.width * 0.72f, size.height * 0.65f))

        val secondaryRoadColor = Color(0xFF1E3A4C).copy(alpha = 0.6f)
        val highwayColor = Color(0xFFD48B38).copy(alpha = 0.7f)

        drawLine(secondaryRoadColor, Offset(0f, size.height * 0.4f), Offset(size.width, size.height * 0.25f), strokeWidth = 1.5.dp.toPx())
        drawLine(secondaryRoadColor, Offset(size.width * 0.3f, 0f), Offset(size.width * 0.25f, size.height), strokeWidth = 1.5.dp.toPx())
        drawLine(secondaryRoadColor, Offset(size.width * 0.8f, 0f), Offset(size.width * 0.85f, size.height), strokeWidth = 1.5.dp.toPx())

        drawLine(highwayColor, Offset(size.width * 0.25f, size.height * 0.6f), Offset(size.width * 0.5f, size.height * 0.35f), strokeWidth = 3.dp.toPx())
        drawLine(highwayColor, Offset(size.width * 0.5f, size.height * 0.35f), Offset(size.width * 0.75f, size.height * 0.45f), strokeWidth = 3.dp.toPx())

        drawCircle(Color(0xFFE53935), radius = 5.dp.toPx(), center = Offset(size.width * 0.25f, size.height * 0.6f))
        drawCircle(Color.White, radius = 5.dp.toPx(), center = Offset(size.width * 0.75f, size.height * 0.45f))
        drawCircle(Color.Black, radius = 2.5.dp.toPx(), center = Offset(size.width * 0.75f, size.height * 0.45f))
    }
}

@Composable
private fun BattleIcon(modifier: Modifier = Modifier.size(16.dp), tint: Color = LocalContentColor.current) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 1.8.dp.toPx()
        // Blade 1: from top-left to bottom-right
        drawLine(
            color = tint,
            start = Offset(w * 0.15f, h * 0.15f),
            end = Offset(w * 0.85f, h * 0.85f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        // Guard 1: perpendicular to blade 1 near hilt
        drawLine(
            color = tint,
            start = Offset(w * 0.58f, h * 0.74f),
            end = Offset(w * 0.74f, h * 0.58f),
            strokeWidth = stroke * 1.2f,
            cap = StrokeCap.Round
        )
        // Blade 2: from top-right to bottom-left
        drawLine(
            color = tint,
            start = Offset(w * 0.85f, h * 0.15f),
            end = Offset(w * 0.15f, h * 0.85f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        // Guard 2: perpendicular to blade 2 near hilt
        drawLine(
            color = tint,
            start = Offset(w * 0.42f, h * 0.74f),
            end = Offset(w * 0.26f, h * 0.58f),
            strokeWidth = stroke * 1.2f,
            cap = StrokeCap.Round
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ExploreScreen(modifier: Modifier) {
    var selectedExploreTab by rememberSaveable { mutableIntStateOf(0) }
    var isRecording by rememberSaveable { mutableStateOf(false) }
    val exploreTabs = listOf(
        "Popular",
        "All",
        "Turf",
        "Segments",
        "Local Roads",
        "Action",
        "Meets",
        "Tracks",
        "Layers"
    )

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            MapSurface(Modifier.fillMaxSize())

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
                    onClick = { isRecording = !isRecording },
                    containerColor = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primaryContainer,
                    contentColor = if (isRecording) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = MaterialTheme.shapes.large,
                ) {
                    Text(
                        if (isRecording) "STOP" else "REC",
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
                // Reference: https://m3.material.io/components/button-groups/overview
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                ) {
                    exploreTabs.forEachIndexed { index, label ->
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
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelMedium,
                                maxLines = 1,
                            )
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
                        MusicCard()
                    }
                }
            }
        }
    }
}

private const val DARK_MAP_STYLE = """[
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

@Composable
private fun MapSurface(modifier: Modifier = Modifier) {
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun MusicCard() {
    val context = LocalContext.current
    val hasNotificationAccess = remember(context) {
        val flat = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        flat != null && (flat.contains(context.packageName) || flat.contains("rapp.rippin.android2") || flat.contains("app.rippin.android"))
    }

    val nowPlaying by MediaPlayback.nowPlaying.collectAsState()
    LaunchedEffect(nowPlaying.isPlaying) {
        while (nowPlaying.isPlaying) {
            delay(500)
            MediaPlayback.refresh()
        }
    }
    LaunchedEffect(hasNotificationAccess) {
        if (hasNotificationAccess) {
            try {
                NotificationListenerService.requestRebind(ComponentName(context, MediaAccessService::class.java))
                MediaPlayback.refresh()
            } catch (_: Exception) {}
        }
    }
    val controller = nowPlaying.controller
    val albumArt = nowPlaying.albumArt
    val progress = if (nowPlaying.durationMs > 0) (nowPlaying.positionMs.toFloat() / nowPlaying.durationMs).coerceIn(0f, 1f) else 0f

    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFF1E222A)
        ),
        shape = RoundedCornerShape(26.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(Modifier.fillMaxWidth()) {
            // Android Media Notification Background: Album Art with subtle dark scrim
            if (albumArt != null) {
                Image(
                    bitmap = albumArt.asImageBitmap(),
                    contentDescription = "Album art background",
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                // Horizontal scrim to keep left-side titles and controls legible
                Box(
                    Modifier
                        .matchParentSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.82f),
                                    Color.Black.copy(alpha = 0.58f),
                                    Color.Black.copy(alpha = 0.25f)
                                )
                            )
                        )
                )
                // Vertical gradient scrim for bottom scrubber/buttons
                Box(
                    Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.25f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )
            } else {
                Box(
                    Modifier
                        .matchParentSize()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFF243242),
                                    Color(0xFF1A1F29),
                                    Color(0xFF12151B)
                                )
                            )
                        )
                )
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (!hasNotificationAccess) {
                    FilledTonalButton(
                        onClick = {
                            context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.Notifications, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Grant Notification Access for Music", style = MaterialTheme.typography.labelMedium)
                    }
                }

                // Row 1: Header (Music Note Icon & "This phone" Output Switcher Chip)
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        Icons.Default.MusicNote,
                        contentDescription = "Media",
                        tint = Color.White.copy(alpha = 0.92f),
                        modifier = Modifier.size(20.dp)
                    )

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.22f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.PhoneAndroid,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(
                                "This phone",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                }

                // Row 2: Track Title & Artist (Left) + Play/Pause Button (Right)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(Modifier.weight(1f).padding(end = 16.dp)) {
                        Text(
                            text = nowPlaying.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = nowPlaying.artist,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.80f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Native Android 13/14 rounded square Play/Pause button
                    Surface(
                        onClick = {
                            if (nowPlaying.isPlaying) controller?.transportControls?.pause() else controller?.transportControls?.play()
                        },
                        shape = RoundedCornerShape(18.dp),
                        color = Color.White.copy(alpha = 0.94f),
                        modifier = Modifier.size(54.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (nowPlaying.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (nowPlaying.isPlaying) "Pause" else "Play",
                                tint = Color(0xFF1B1A1E),
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }

                // Row 3: Scrubber Line with Controls (Skip Previous, Wavy Indicator, Skip Next, Star, Shuffle)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { controller?.transportControls?.skipToPrevious() },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            Icons.Default.SkipPrevious,
                            contentDescription = "Previous track",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Box(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                            .height(32.dp)
                            .pointerInput(Unit) {
                                detectTapGestures { offset ->
                                    if (nowPlaying.durationMs > 0) {
                                        val fraction = (offset.x / size.width).coerceIn(0f, 1f)
                                        controller?.transportControls?.seekTo((fraction * nowPlaying.durationMs).toLong())
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        LinearWavyProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.fillMaxWidth().height(14.dp),
                            color = Color.White,
                            trackColor = Color.White.copy(alpha = 0.32f),
                        )
                    }

                    IconButton(
                        onClick = { controller?.transportControls?.skipToNext() },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            Icons.Default.SkipNext,
                            contentDescription = "Next track",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            Icons.Default.StarBorder,
                            contentDescription = "Favorite",
                            tint = Color.White.copy(alpha = 0.88f),
                            modifier = Modifier.size(21.dp)
                        )
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            Icons.Default.Shuffle,
                            contentDescription = "Shuffle",
                            tint = Color.White.copy(alpha = 0.88f),
                            modifier = Modifier.size(21.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SquadsScreen(modifier: Modifier) {
    var selectedSquadTab by rememberSaveable { mutableIntStateOf(0) }
    var selectedSubTab by rememberSaveable { mutableIntStateOf(0) }
    val squadTabs = listOf("Feed", "Stats", "Members", "Poker")
    val subTabs = listOf("Posts", "Rides", "Photos 6")

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
                    // Card 1: Featured JB Moto
                    Card(
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
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
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
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
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
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
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
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
                    squadTabs.forEachIndexed { index, tab ->
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
                    subTabs.forEachIndexed { index, tab ->
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

@Composable
private fun WhatIsHappeningComposer() {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF333333),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🏍", fontSize = 18.sp)
                    }
                }
                Text("What's happening?", color = Color(0xFF888888), fontSize = 15.sp)
            }

            Row(
                Modifier.padding(start = 52.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(Icons.Default.CropFree, null, tint = Color(0xFFD48B38), modifier = Modifier.size(20.dp))
                Icon(Icons.Default.Poll, null, tint = Color(0xFF29B6F6), modifier = Modifier.size(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommunityScreen(modifier: Modifier) {
    var selectedFilter by rememberSaveable { mutableIntStateOf(0) }
    var highlightsExpanded by rememberSaveable { mutableStateOf(true) }
    val filters = listOf("💬 Status", "/\\ Rides", "📅 Events")

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
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
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
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
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
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
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

            // Filter Pills
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    filters.forEachIndexed { index, filter ->
                        val isSelected = selectedFilter == index
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF1A1A1E),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
                            modifier = Modifier.clickable { selectedFilter = index }
                        ) {
                            Text(
                                filter,
                                color = if (isSelected) Color.White else Color(0xFF888888),
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
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
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
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

@Composable
private fun WeekStatBox(icon: ImageVector, value: String, label: String) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        modifier = Modifier.size(width = 68.dp, height = 74.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, null, tint = Color(0xFFE53935), modifier = Modifier.size(16.dp))
            Spacer(Modifier.height(4.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            Text(label, fontSize = 10.sp, color = Color(0xFF888888))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(modifier: Modifier) {
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
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)),
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
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
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
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
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
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
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
