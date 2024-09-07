package com.example.learnquest.Utils.database

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseClient1 {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://xyzcompany.supabase.co",
        supabaseKey = "public-anon-key"
    ) {

        install(Postgrest)
        //install other modules
    }

    fun getClient():SupabaseClient{
        return supabase;
    }
}