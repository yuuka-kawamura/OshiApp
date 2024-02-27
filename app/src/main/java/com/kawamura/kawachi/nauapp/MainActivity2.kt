package com.kawamura.kawachi.nauapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity(),TotalAmountListener {

    //９）追加リストと、RecyclerViewと、アダプターを用意
    private var addList2 =ArrayList<TodoData2>() //空っぽのリストを用意<型はデータクラス>
    private lateinit var recyclerView : RecyclerView
    private var recyclerAdapter = RecyclerAdapter2(addList2, this)//アダプターに追加リストをセット

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //１０）viewの取得、アダプターにセット、レイアウトにセット
        recyclerView = findViewById(R.id.rv)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val et : EditText = findViewById(R.id.et)
        val et2:EditText=findViewById(R.id.et2)
        val et3:EditText=findViewById(R.id.et3)
        // 合計金額を表示するテキストビュー
        val totalAmountText: TextView = findViewById(R.id.totalamount_text)


        //１１）追加ボタンを押したら～
        val btnAdd : Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {


            // et2の入力が整数であることを確認
            val countInput: Int = if (et2.text.toString().isNotBlank()) et2.text.toString().toInt() else 0

            // et3の入力が整数であることを確認
            val priceInput: Int = if (et3.text.toString().isNotBlank()) et3.text.toString().toInt() else 0

            val data2 = TodoData2(
                myTodo = et.text.toString(),
                myCount = countInput,
                myPrice = priceInput
            )



            addList2.add(data2)
            recyclerAdapter.notifyItemInserted(addList2.lastIndex) //表示を更新（リストの最後に挿入）
            // スクロール位置を一番下に移動しないように修正
            recyclerView.scrollToPosition(addList2.lastIndex)

            et.text = null
            et2.text=null
            et3.text=null


            // 合計金額を計算 メソッドcalculateTotalAmount
            val totalAmount = calculateTotalAmount()

            // 合計金額を表示
            totalAmountText.text = "合計金額: ¥$totalAmount"

        }
    }

    // et3に入力された金額の合計を計算するメソッド
    private fun calculateTotalAmount(): Int {
        var totalAmount = 0

        // リスト内の各アイテムの金額を合計
        for (item in addList2) {
            totalAmount += item.myPrice
        }

        return totalAmount
    }

    override fun onTotalAmountChanged(totalAmount: Int) {
        // 合計金額が変更されたときの処理をここに追加
        val totalAmountText: TextView = findViewById(R.id.totalamount_text)
        totalAmountText.text = "合計金額: ¥$totalAmount"
    }

}
