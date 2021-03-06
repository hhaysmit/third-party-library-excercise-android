package io.intrepid.thirdpartylibraryexcercise;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements MainContract.View, DownloadCatImageTask.Callback {

    ImageView imageView;
    Button getNewCatButton;

    private MainPresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter();
        presenter.bindView(this);

        imageView = (ImageView) findViewById(R.id.image_view);
        getNewCatButton = (Button) findViewById(R.id.new_cat_button);
        getNewCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNewCatButtonClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    public void onNewCatButtonClick() {
        presenter.onNewCatButtonClick();
    }

    @Override
    public void showLoadingIndicator() {
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.show();
    }

    @Override
    public void dismissLoadingIndicator() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void loadImageFromUrl(String url) {
        DownloadCatImageTask downloadCatImageTask = new DownloadCatImageTask();
        downloadCatImageTask.setCallback(this);
        downloadCatImageTask.execute(url);
    }

    @Override
    public void onImageResult(Bitmap bitmap) {
        dismissLoadingIndicator();
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbindView();
    }
}
