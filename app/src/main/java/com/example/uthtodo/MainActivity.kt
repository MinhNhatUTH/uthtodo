package com.example.uthtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay

val Xanh = Color(0xFF3A5AE1)
val XanhNhat = Color(0xFFE7ECFA)
val NenTim = Color(0xFFEBEEFA)
val Den = Color(0xFF151515)
val Xam = Color(0xFF787878)
val XamNhat = Color(0xFFE5E5E5)
val Cam = Color(0xFF8A7256)
val CamNhat = Color(0xFFF8EBD8)

class TrangGioiThieu(
    val tieuDe: String,
    val moTa: String,
    val chuNut: String
)

class KhoGioiThieu {
    private val danhSach = listOf(
        TrangGioiThieu( //bước 1
            "Mọi việc, gọn trong một chỗ",
            "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào.",
            "Tiếp tục"
        ),
        TrangGioiThieu( // bước 2
            "Chia theo Học tập và Cuộc sống",
            "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần lo gì.",
            "Tiếp tục"
        ),
        TrangGioiThieu(   //bước 3
            "Nhắc đúng lúc",
            "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.",
            "Bắt đầu ngay"
        )
    )

    fun laySoTrang() = danhSach.size 
    fun layTrang(viTri: Int) = danhSach[viTri]
    fun laTrangCuoi(viTri: Int) = viTri == danhSach.size - 1
}

object TenManHinh {
    const val MAN_HINH_CHAO = "man_hinh_chao"
    const val GIOI_THIEU = "gioi_thieu"
    const val TRANG_CHU = "trang_chu"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { UngDungUthTodo() }
    }
}

@Composable
fun UngDungUthTodo() {
    val dieuHuong = rememberNavController()
    val kho = remember { KhoGioiThieu() }

    NavHost(navController = dieuHuong, startDestination = TenManHinh.MAN_HINH_CHAO) {
        composable(TenManHinh.MAN_HINH_CHAO) {
            ManHinhChao(dieuHuong)
        }
        composable(
            route = TenManHinh.GIOI_THIEU + "/{viTri}",
            arguments = listOf(navArgument("viTri") { type = NavType.IntType })
        ) { manHinh ->
            val viTri = manHinh.arguments?.getInt("viTri") ?: 0
            ManHinhGioiThieu(dieuHuong, kho, viTri)
        }
        composable(TenManHinh.TRANG_CHU) {
            ManHinhTrangChu()
        }
    }
}

@Composable
fun BieuTuong(kichThuoc: Dp, nen: Color, net: Color) {
    Box(
        modifier = Modifier
            .size(kichThuoc)
            .clip(RoundedCornerShape(kichThuoc / 4))
            .background(nen),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(kichThuoc / 10)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(kichThuoc / 14)
            ) {
                Box(Modifier.size(kichThuoc / 5).clip(CircleShape).background(net))
                Box(Modifier.size(kichThuoc / 3, kichThuoc / 14).clip(CircleShape).background(net))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(kichThuoc / 14)
            ) {
                Box(Modifier.size(kichThuoc / 5).border(1.dp, net, CircleShape))
                Box(
                    Modifier
                        .size(kichThuoc / 4, kichThuoc / 14)
                        .clip(CircleShape)
                        .background(net.copy(alpha = 0.5f))
                )
            }
        }
    }
}

@Composable
fun ManHinhChao(dieuHuong: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        dieuHuong.navigate(TenManHinh.GIOI_THIEU + "/0") {
            popUpTo(TenManHinh.MAN_HINH_CHAO) { inclusive = true }
        }
    }

    Box(Modifier.fillMaxSize().background(Xanh)) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BieuTuong(96.dp, Color.White, Xanh)
            Spacer(Modifier.height(20.dp))
            Text("UTH Todo", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("Việc học, việc nhà — gọn gàng mỗi ngày", color = Color.White, fontSize = 13.sp)
        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.width(120.dp).height(3.dp).background(Color.White.copy(alpha = 0.3f))) {
                Box(Modifier.fillMaxWidth(0.35f).fillMaxHeight().background(Color.White))
            }
            Spacer(Modifier.height(10.dp))
            Text("Phiên bản 2.1.0", color = Color.White, fontSize = 11.sp)
        }
    }
}

@Composable
fun ManHinhGioiThieu(dieuHuong: NavController, kho: KhoGioiThieu, viTri: Int) {
    val trang = kho.layTrang(viTri)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BieuTuong(28.dp, Xanh, Color.White)
                Spacer(Modifier.width(8.dp))
                Text("UTH ", color = Den, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Todo", color = Xanh, fontSize = 16.sp)
            }
            TextButton(onClick = {
                dieuHuong.navigate(TenManHinh.TRANG_CHU) { popUpTo(0) }
            }) {
                Text("Bỏ qua", color = Xam)
            }
        }

        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            HinhMinhHoa(viTri)
        }

        Text(trang.tieuDe, color = Den, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text(trang.moTa, color = Xam, fontSize = 14.sp)
        Spacer(Modifier.height(28.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            for (i in 0 until kho.laySoTrang()) {
                Box(
                    modifier = Modifier
                        .size(if (i == viTri) 24.dp else 6.dp, 6.dp)
                        .clip(CircleShape)
                        .background(if (i == viTri) Xanh else Color.LightGray)
                )
            }
        }
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (kho.laTrangCuoi(viTri)) {
                    dieuHuong.navigate(TenManHinh.TRANG_CHU) { popUpTo(0) }
                } else {
                    dieuHuong.navigate(TenManHinh.GIOI_THIEU + "/" + (viTri + 1))
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Xanh, contentColor = Color.White)
        ) {
            Text(trang.chuNut, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun HinhMinhHoa(viTri: Int) {
    Box(
        modifier = Modifier
            .size(200.dp, 240.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(NenTim),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (viTri) {
                0 -> {
                    for (i in 0 until 3) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(if (i == 0) Xanh else Color.White)
                                    .border(2.dp, if (i == 0) Xanh else XamNhat, CircleShape)
                            )
                            Spacer(Modifier.width(10.dp))
                            Box(Modifier.weight(1f).height(6.dp).clip(CircleShape).background(XamNhat))
                        }
                    }
                }
                1 -> {
                    TheViec("Học tập", Xanh, XanhNhat)
                    TheViec("Cuộc sống", Cam, CamNhat)
                }
                else -> {
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = Xanh,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = "Ôn bài Toán lúc 19:00",
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TheViec(ten: String, mauChu: Color, mauNen: Color) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(12.dp)
    ) {
        Text(
            text = ten,
            color = mauChu,
            fontSize = 11.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(mauNen)
                .padding(horizontal = 8.dp, vertical = 3.dp)
        )
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth(0.85f).height(6.dp).clip(CircleShape).background(XamNhat))
    }
}

@Composable
fun ManHinhTrangChu() {
    Box(Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        Text("Trang chủ UTH Todo", color = Den, fontSize = 20.sp)
    }
}