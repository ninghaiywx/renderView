package com.example.ywx.renderview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private RenderView renderView;
    private float[] data={56.2f,42.0f,45.5f,52.4f,49.4f,44.0f};
    private String[] text={"发育","推进","生存","输出","综合","KDA"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        renderView=(RenderView)findViewById(R.id.render);
        renderView.setData(data);
        renderView.setTitle(text);
        renderView.invalidate();
    }
}
