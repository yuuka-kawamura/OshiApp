package com.kawamura.kawachi.nauapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.kawamura.kawachi.nauapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var selectedImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityHomeBinding.inflate(layoutInflater).apply { setContentView(this.root) }
        selectedImage = binding.pickImageButton  // ここでviewbuttonをImageViewに変更
        val toMotimonointent:Intent=Intent(this,MainActivity::class.java)
        val toKaimonointent:Intent=Intent(this,MainActivity2::class.java)

        binding.button.setOnClickListener {
            startActivity(toMotimonointent)
        }
        binding.button2.setOnClickListener {
            startActivity(toKaimonointent)
        }

        // 画像選択のためのActivityResultLauncherを作成
        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    // 選択された画像をImageViewに設定
                    selectedImage.setImageURI(it)
                }
            }
        }

        // 画像選択ボタンが押された時の処理
        binding.pickImageButton.setOnClickListener {
            // ギャラリーアプリを開くIntent
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            getContent.launch(intent)
        }
    }

    
}