package com.example.hirely.presentation.ui.screen.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state consumed by WalletScreen. Success carries exactly what the
 * screen renders -- balance + the transaction list -- so the composable
 * never needs to know about the repository's raw response shape.
 */
sealed interface WalletUiState {
    data object Loading : WalletUiState
    data class Success(
        val balance: Double,
        val transactions: List<WalletTransaction>
    ) : WalletUiState
    data class Error(val message: String) : WalletUiState
    data object Empty : WalletUiState
}

/**
 * Contract for the wallet data source. Implement against your existing
 * networking stack (Retrofit/Ktor) and map the response into
 * balance + List<WalletTransaction>.
 */
interface WalletRepository {
    suspend fun getWalletBalance(): Result<Double>
    suspend fun getTransactions(): Result<List<WalletTransaction>>
}

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WalletUiState>(WalletUiState.Loading)
    val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()

    init {
        loadWallet()
    }

    fun loadWallet() {
        viewModelScope.launch {
            _uiState.value = WalletUiState.Loading

            val balanceResult = walletRepository.getWalletBalance()
            val transactionsResult = walletRepository.getTransactions()

            val balance = balanceResult.getOrNull()
            val transactions = transactionsResult.getOrNull()

            when {
                balanceResult.isFailure -> _uiState.value = WalletUiState.Error(
                    balanceResult.exceptionOrNull()?.message ?: "Couldn't load your balance."
                )
                transactionsResult.isFailure -> _uiState.value = WalletUiState.Error(
                    transactionsResult.exceptionOrNull()?.message ?: "Couldn't load transactions."
                )
                balance == 0.0 && transactions.isNullOrEmpty() -> _uiState.value = WalletUiState.Empty
                else -> _uiState.value = WalletUiState.Success(
                    balance = balance ?: 0.0,
                    transactions = transactions.orEmpty()
                )
            }
        }
    }

    /** Hook for the Request Payment action -- wire to the real withdrawal flow/navigation. */
    fun onRequestPayment() {
        // e.g. navController.navigate(WalletDestinations.RequestPayment)
    }
}