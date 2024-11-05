package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources.AnalyzePDFActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.EqualSpacingItemDecoration;
import com.example.learnquest.R;
import com.example.learnquest.model.studyResource.ChatPDFClient;
import com.example.learnquest.model.studyResource.Message;
import com.example.learnquest.model.studyResource.StudyResource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AnalyzePDF_Activity extends AppCompatActivity {
    StudyResource studyResource;
    MessageAdapter adapter;
    ChatPDFClient client;
    ProgressBar progressBar;
    Button btnSend;
    RecyclerView messageView;

    private View rootLayout;

    private final String API_KEY = "sec_4MtXQfUV8mGlxwLsZfxZgd8yaEYTtQPl";

    BlockingQueue<Message> incomingMessages;

    TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analyze_pdf);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rootLayout = findViewById(R.id.main_scrollView);
        btnSend = findViewById(R.id.btnSend);



        txtMessage = findViewById(R.id.edtMessage);
        progressBar = findViewById(R.id.resources_progressBar);
        messageView = findViewById(R.id.messageView);
        progressBar.setVisibility(View.GONE);

        incomingMessages = new LinkedBlockingQueue<>();
        client = new ChatPDFClient(API_KEY, this::onReceived, this::waiting, this::received);

        Intent intent = getIntent();
        if (intent != null){
            studyResource = (StudyResource) intent.getSerializableExtra("studyResource");
            client.uploadUrl(studyResource.getURL());
        }

        adapter = new MessageAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        messageView.setLayoutManager(layoutManager);
        messageView.addItemDecoration(new EqualSpacingItemDecoration(10));
        messageView.setAdapter(adapter);

        Thread thread = new Thread(this::addThread);
        thread.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootLayout.getWindowVisibleDisplayFrame(r);
                int heightDiff = rootLayout.getRootView().getHeight() - (r.bottom - r.top);

                // Check if the height difference is significant, indicating that the keyboard is visible
                if (heightDiff > 200) { // You can adjust this threshold as needed
                    // Keyboard is visible
                    resizeUIForKeyboard();
                    // Adjust layout or perform actions here
                } else {
                    // Keyboard is hidden
                    // Reset any adjustments made when the keyboard was visible
                }
            }
        });
    }

    private void resizeUIForKeyboard() {
        // Adjust the EditText height and margins as needed
        ViewGroup.LayoutParams params = txtMessage.getLayoutParams();
        params.height = 100; // Set to a smaller height when the keyboard is visible
        txtMessage.setLayoutParams(params);

        // Optionally, adjust the Button position or size
        ViewGroup.MarginLayoutParams buttonParams = (ViewGroup.MarginLayoutParams) btnSend.getLayoutParams();
        buttonParams.topMargin = 16; // Adjust margin if needed
        btnSend.setLayoutParams(buttonParams);

        // Other UI adjustments can be made here
    }

    public void waiting(){
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
    }

    public void received(){
        runOnUiThread(() -> progressBar.setVisibility(View.GONE));
    }

    public void onReceived(Message message){
        incomingMessages.add(message);
    }

    public void addThread(){
        while(true){
            try {
                Message message = incomingMessages.take();
                runOnUiThread(() -> addMessage(message));
            } catch (InterruptedException e) {
                Log.e("Custom", "Write thread interrupted. ");
                return;
            }
        }
    }

    public void addMessage(Message message){
        adapter.add(message);
    }

    public void onSendClick(View view) {
        String message = txtMessage.getText().toString();
        client.sendMessage(message);
        addMessage(Message.fromMe(message));
    }
}