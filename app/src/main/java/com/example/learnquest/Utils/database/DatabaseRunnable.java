package com.example.learnquest.Utils.database;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public interface DatabaseRunnable{
   void run(Call<List<?>> call, Response<List<?>> response, Throwable throwable);
}
