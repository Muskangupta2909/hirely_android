package com.example.hirely.presentation.ui.screen.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.NumberFormat
import java.util.Locale


// ---------------------------------------------------------------------------
// Models
// ---------------------------------------------------------------------------

enum class TransactionStatus(val label: String) {
    ALL("All"),
    COMPLETED("Completed"),
    PARTIAL_COMPLETED("Partial Completed"),
    DROPPED("Dropped"),
    WITHDRAWAL("Withdrawal"),
    ADVANCES("Advances"),
    FINAL_INSPECTION("Final Inspection"),
    BONUS("Bonus")
}


data class WalletTransaction(
    val id: String,
    val title: String,
    val date: String,
    val amount: Double,
    val status: TransactionStatus
)


// ---------------------------------------------------------------------------
// Colors
// ---------------------------------------------------------------------------

private val WalletTealStart = Color(0xFF2E6B7A)
private val WalletTealEnd = Color(0xFF1B4650)

private val ScreenBackground = Color(0xFFF3F5F7)
private val RowBackground = Color(0xFFF6F7F9)


// ---------------------------------------------------------------------------
// Wallet Screen
// ---------------------------------------------------------------------------

@Composable
fun WalletScreen(
    onBackClick: () -> Unit,
    viewModel: WalletViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {

        is WalletUiState.Loading -> {
            WalletLoading()
        }

        is WalletUiState.Error -> {
            WalletError(
                message = state.message,
                onRetry = viewModel::loadWallet
            )
        }

        is WalletUiState.Empty -> {
            WalletEmpty()
        }

        is WalletUiState.Success -> {
            WalletScreenContent(
                balance = state.balance,
                transactions = state.transactions,
                onBackClick = onBackClick,
                onRefreshClick = viewModel::loadWallet,
                onRequestPaymentClick = viewModel::onRequestPayment,
                onSearchClick = {
                    // Search functionality can be connected later
                },
                onCalendarClick = {
                    // Date picker can be connected later
                }
            )
        }
    }
}


// ---------------------------------------------------------------------------
// Main Wallet Content
// ---------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreenContent(
    balance: Double,
    transactions: List<WalletTransaction>,
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onRequestPaymentClick: () -> Unit,
    onSearchClick: () -> Unit,
    onCalendarClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedStatus by remember {
        mutableStateOf(TransactionStatus.ALL)
    }

    var isFilterMenuOpen by remember {
        mutableStateOf(false)
    }

    val filteredTransactions = remember(
        transactions,
        selectedStatus
    ) {

        if (selectedStatus == TransactionStatus.ALL) {
            transactions
        } else {
            transactions.filter {
                it.status == selectedStatus
            }
        }
    }


    Scaffold(
        modifier = modifier,
        containerColor = ScreenBackground,

        topBar = {
            WalletTopBar(
                onBackClick = onBackClick,
                onRefreshClick = onRefreshClick
            )
        }

    ) { paddingValues ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            ),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            // ---------------------------------------------------------
            // Balance
            // ---------------------------------------------------------

            item {

                WalletBalanceCard(
                    balance = balance
                )
            }


            // ---------------------------------------------------------
            // Request Payment
            // ---------------------------------------------------------

            item {

                RequestPaymentButton(
                    onClick = onRequestPaymentClick
                )
            }


            // ---------------------------------------------------------
            // Transactions Header
            // ---------------------------------------------------------

            item {

                Box {

                    TransactionsHeader(
                        onSearchClick = onSearchClick,
                        onFilterClick = {
                            isFilterMenuOpen = true
                        },
                        onCalendarClick = onCalendarClick
                    )


                    DropdownMenu(

                        expanded = isFilterMenuOpen,

                        onDismissRequest = {
                            isFilterMenuOpen = false
                        },

                        modifier = Modifier
                            .width(260.dp)
                            .background(Color.White)

                    ) {

                        FilterByStatusPanel(
                            selectedStatus = selectedStatus,

                            onStatusSelected = { status ->

                                selectedStatus = status
                                isFilterMenuOpen = false
                            }
                        )
                    }
                }
            }


            // ---------------------------------------------------------
            // Transactions
            // ---------------------------------------------------------

            if (filteredTransactions.isEmpty()) {

                item {

                    Text(
                        text = "No transactions for this filter.",

                        style = MaterialTheme.typography.bodyMedium,

                        color = MaterialTheme.colorScheme.onSurfaceVariant,

                        modifier = Modifier.padding(
                            vertical = 24.dp
                        )
                    )
                }

            } else {

                items(
                    items = filteredTransactions,
                    key = {
                        it.id
                    }
                ) { transaction ->

                    TransactionRow(
                        transaction = transaction
                    )
                }
            }


            item {

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }
    }
}


// ---------------------------------------------------------------------------
// Top App Bar
// ---------------------------------------------------------------------------

@Composable
private fun WalletTopBar(
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit
) {

    Surface(
        color = Color.White
    ) {

        Box(

            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 8.dp),

            contentAlignment = Alignment.Center
        ) {

            Row(

                modifier = Modifier.fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically
            ) {


                // ---------------------------------------------------------
                // Back Button
                // ---------------------------------------------------------

                TextButton(
                    onClick = onBackClick,

                    modifier = Modifier
                        .heightIn(min = 48.dp)
                ) {

                    Text(
                        text = "‹",
                        fontSize = 30.sp,
                        color = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = "Back",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                }


                Spacer(
                    modifier = Modifier.weight(1f)
                )


                // ---------------------------------------------------------
                // Refresh
                // ---------------------------------------------------------

                TextButton(
                    onClick = onRefreshClick,

                    modifier = Modifier
                        .heightIn(min = 48.dp)
                ) {

                    Text(
                        text = "↻",
                        fontSize = 22.sp,
                        color = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = "Refresh",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


            // ---------------------------------------------------------
            // Center Title
            // ---------------------------------------------------------

            Text(
                text = "Wallet",

                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),

                color = Color.Black
            )
        }
    }
}


// ---------------------------------------------------------------------------
// Wallet Balance Card
// ---------------------------------------------------------------------------

@Composable
private fun WalletBalanceCard(
    balance: Double,
    modifier: Modifier = Modifier
) {

    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance(Locale.US)
    }


    Box(

        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(
                Brush.verticalGradient(
                    listOf(
                        WalletTealStart,
                        WalletTealEnd
                    )
                )
            )
            .padding(24.dp)
    ) {

        Row(

            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            // ---------------------------------------------------------
            // Card Label
            // ---------------------------------------------------------

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(

                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(
                            Color.White.copy(
                                alpha = 0.15f
                            )
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "▣",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }


                Spacer(
                    modifier = Modifier.width(8.dp)
                )


                Text(
                    text = "WALLET BALANCE",

                    color = Color.White,

                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )
                )
            }


            // ---------------------------------------------------------
            // Wallet Badge
            // ---------------------------------------------------------

            Box(

                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        Color.White.copy(
                            alpha = 0.18f
                        )
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "▰",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }


        // ---------------------------------------------------------
        // Amount
        // ---------------------------------------------------------

        Text(

            text = currencyFormat.format(balance),

            color = Color.White,

            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold
            ),

            modifier = Modifier.padding(
                top = 56.dp
            )
        )
    }
}


// ---------------------------------------------------------------------------
// Request Payment
// ---------------------------------------------------------------------------

@Composable
private fun RequestPaymentButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Button(

        onClick = onClick,

        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),

        shape = RoundedCornerShape(28.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = WalletTealStart,
            contentColor = Color.White
        )
    ) {

        Text(
            text = "Request Payment",

            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


// ---------------------------------------------------------------------------
// Transactions Header
// ---------------------------------------------------------------------------

@Composable
private fun TransactionsHeader(
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit,
    onCalendarClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(

        modifier = modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Transactions",

            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),

            color = Color.Black
        )


        Row {

            // Search
            TextButton(
                onClick = onSearchClick
            ) {

                Text(
                    text = "⌕",
                    fontSize = 23.sp,
                    color = Color(0xFF3A3A3A)
                )
            }


            // Filter
            TextButton(
                onClick = onFilterClick
            ) {

                Text(
                    text = "☷",
                    fontSize = 22.sp,
                    color = WalletTealStart
                )
            }


            // Calendar
            TextButton(
                onClick = onCalendarClick
            ) {

                Text(
                    text = "□",
                    fontSize = 20.sp,
                    color = Color(0xFF3A3A3A)
                )
            }
        }
    }
}


// ---------------------------------------------------------------------------
// Filter By Status
// ---------------------------------------------------------------------------

@Composable
private fun FilterByStatusPanel(
    selectedStatus: TransactionStatus,
    onStatusSelected: (TransactionStatus) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(
            vertical = 8.dp
        )
    ) {

        Text(

            text = "Filter by Status",

            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),

            color = Color.Black,

            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        )


        TransactionStatus.entries.forEach { status ->

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .clickable {
                        onStatusSelected(status)
                    }
                    .padding(
                        horizontal = 12.dp
                    ),

                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(

                    selected = selectedStatus == status,

                    onClick = {
                        onStatusSelected(status)
                    },

                    colors = RadioButtonDefaults.colors(
                        selectedColor = WalletTealStart
                    )
                )


                Spacer(
                    modifier = Modifier.width(4.dp)
                )


                Text(

                    text = status.label,

                    style = MaterialTheme.typography.bodyLarge,

                    color = Color(0xFF2A2A2A)
                )
            }
        }
    }
}


// ---------------------------------------------------------------------------
// Transaction Row
// ---------------------------------------------------------------------------

@Composable
private fun TransactionRow(
    transaction: WalletTransaction,
    modifier: Modifier = Modifier
) {

    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance(Locale.US)
    }


    Card(

        modifier = modifier.fillMaxWidth(),

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor = RowBackground
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                ),

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically
        ) {


            Column {

                Text(

                    text = transaction.title,

                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),

                    color = Color.Black
                )


                Spacer(
                    modifier = Modifier.height(4.dp)
                )


                Text(

                    text = transaction.date,

                    style = MaterialTheme.typography.bodyMedium,

                    color = Color(0xFF8A8A8A)
                )
            }


            Text(

                text = currencyFormat.format(
                    transaction.amount
                ),

                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),

                color = if (
                    transaction.amount >= 0
                ) {
                    Color(0xFF1B7A3D)
                } else {
                    Color(0xFFB3261E)
                }
            )
        }
    }
}


// ---------------------------------------------------------------------------
// Loading
// ---------------------------------------------------------------------------

@Composable
private fun WalletLoading() {

    Box(

        modifier = Modifier.fillMaxSize(),

        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            color = WalletTealStart
        )
    }
}


// ---------------------------------------------------------------------------
// Error
// ---------------------------------------------------------------------------

@Composable
private fun WalletError(
    message: String,
    onRetry: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Couldn't load your wallet",

            style = MaterialTheme.typography.titleMedium
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        Text(

            text = message,

            style = MaterialTheme.typography.bodyMedium,

            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        Button(
            onClick = onRetry
        ) {

            Text(
                text = "Retry"
            )
        }
    }
}


// ---------------------------------------------------------------------------
// Empty
// ---------------------------------------------------------------------------

@Composable
private fun WalletEmpty() {

    Box(

        modifier = Modifier.fillMaxSize(),

        contentAlignment = Alignment.Center
    ) {

        Text(

            text = "No wallet activity yet",

            style = MaterialTheme.typography.titleMedium,

            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun WalletScreenContentPreview() {

    MaterialTheme {

        WalletScreenContent(

            balance = 236.50,

            transactions = listOf(

                WalletTransaction(
                    id = "1",
                    title = "Bonus",
                    date = "15 May 2026",
                    amount = 25.00,
                    status = TransactionStatus.BONUS
                ),

                WalletTransaction(
                    id = "2",
                    title = "John Doe",
                    date = "15 May 2026",
                    amount = 120.00,
                    status = TransactionStatus.COMPLETED
                ),

                WalletTransaction(
                    id = "3",
                    title = "John Doe",
                    date = "15 May 2026",
                    amount = 91.50,
                    status = TransactionStatus.PARTIAL_COMPLETED
                )
            ),

            onBackClick = {},

            onRefreshClick = {},

            onRequestPaymentClick = {},

            onSearchClick = {},

            onCalendarClick = {}
        )
    }
}