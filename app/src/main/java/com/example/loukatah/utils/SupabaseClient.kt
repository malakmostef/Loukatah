package com.example.loukatah.utils

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage

object SupabaseClient {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://rtsypjqehxdsgfbhihwk.supabase.co", // استبدل بالـ URL الخاص بك
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ0c3lwanFlaHhkc2dmYmhpaHdrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDY0NzEwNjQsImV4cCI6MjA2MjA0NzA2NH0.h470NV2YvVlhZ9MSu3i82bz02egq2nc38BtUXixxrz8" // استبدل بالـ Key الخاص بك
    ) {
        install(Storage) // تفعيل ميزة الاستورج
    }

    val storage = client.storage
}