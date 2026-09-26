package com.example.netlib.domain.network

interface NetworkMonitor {
    fun isConnected(): Boolean
}