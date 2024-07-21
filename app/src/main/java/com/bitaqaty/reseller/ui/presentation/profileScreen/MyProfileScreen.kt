package com.bitaqaty.reseller.ui.presentation.profileScreen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.border

@Composable
fun MyProfileScreen(navController: NavController, modifier: Modifier) {
    val myProfileViewModel: MyProfileViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    Profile(onItemClick = {
        navController.navigate(Screen.ChangePasswordScreen.route)
    })
}

@Composable
fun Profile(onItemClick: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(Modifier.padding(top = Dimens.halfDefaultMargin)) {
            ProfileDetails()
        }
        Column(Modifier.padding(top = Dimens.DefaultMargin)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.DefaultMargin, vertical = Dimens.defaultMargin6
                    )
            ) {
                AccountManager("Account Manager",
                    R.drawable.ic_manager_icon, 13, onItemClick = {
                        onItemClick("Account Manager")
                    })
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.DefaultMargin, vertical = Dimens.defaultMargin6
                    )
            ) {
                AccountManager("Change Password",
                    R.drawable.ic_password_svgrepo, 13,
                    onItemClick = {
                        onItemClick("Change Password")
                    })
            }
        }
        Column(Modifier.padding(top = Dimens.padding40)) {
            ProfileFooter("FAQ", R.drawable.ic_check_faq_help_infor)
            ProfileFooter("Terms and Conditions", R.drawable.ic_verified_check_svgre)
            ProfileFooter("Privacy", R.drawable.ic_shield_user_svgrepo)
        }


    }

}

@Composable
fun ProfileDetails() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                vertical = Dimens.DefaultMargin, horizontal = Dimens.DefaultMargin
            ), shape = RoundedCornerShape(Dimens.DefaultMargin10),

        colors = CardDefaults.cardColors(containerColor = border)

    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.halfDefaultMargin, horizontal = Dimens.DefaultMargin)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.halfDefaultMargin),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(2f)


                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.halfDefaultMargin),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(Dimens.profileImage),
                            painter = painterResource(R.drawable.ic_personal_profile),
                            contentDescription = ""
                        )
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Dimens.halfDefaultMargin)
                        ) {
                            Text(
                                text = "Welcome",
                                style = TextStyle(color = LightGrey200, fontSize = 14.sp)
                            )
                            Text(
                                text = "Khalidalisub5",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = "Sub Account",
                        style = TextStyle(
                            color = LightGrey200,
                            fontSize = 14.sp
                        )
                    )
                    Text(
                        text = "Khalid ali",
                        style = TextStyle(
                            color = LightGrey200,
                            fontSize = 14.sp
                        )
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Dimens.halfDefaultMargin, horizontal = Dimens.DefaultMargin
                    )
                    .padding(top = Dimens.padding30)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Last Visit Date",
                        style = TextStyle(color = LightGrey200, fontSize = 14.sp)
                    )
                    Text(
                        text = "Account No.",
                        style = TextStyle(color = LightGrey200, fontSize = 14.sp)
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = Dimens.halfDefaultMargin),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "12-12-2023, 12:59:05 PM",
                        style = TextStyle(color = LightGrey200, fontSize = 12.sp)
                    )
                    Text(
                        modifier = Modifier.padding(top = Dimens.fourDefaultMargin),
                        text = "5604001752",
                        style = TextStyle(color = LightGrey200, fontSize = 12.sp)
                    )
                }
            }
        }
    }
}


@Composable
fun AccountManager(
    text: String, icon: Int, textSize: Int,
    onItemClick: () -> Unit
) {
    Card(
        Modifier.clickable {
            onItemClick.invoke()
        },
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.halfDefaultMargin),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = Dimens.fourDefaultMargin),
                painter = painterResource(icon), contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .padding(
                        vertical = Dimens.DefaultMargin20
                    )
                    .padding(start = Dimens.halfDefaultMargin)
                    .fillMaxWidth()
                    .weight(4f),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = BebeBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize.sp,
                ),
                text = text
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                painter = painterResource(R.drawable.ic_forward_arrow),
                contentDescription = ""
            )

        }
    }
}


@Composable
fun ProfileFooter(text: String, icon: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.DefaultMargin,
            ), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(horizontal = Dimens.halfDefaultMargin),
            painter = painterResource(icon),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(
                    vertical = Dimens.fourDefaultMargin
                )
                .fillMaxWidth(), style = TextStyle(
                textAlign = TextAlign.Start,
                color = BebeBlue,
                fontSize = 14.sp,
            ), text = text
        )
    }
}


