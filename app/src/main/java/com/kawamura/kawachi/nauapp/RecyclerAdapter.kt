package com.kawamura.kawachi.nauapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//５）リストを用意 クラス名(private val 変数名:ArrayList<データクラス>) //or MutableList
class RecyclerAdapter(private val todoList: ArrayList<TodoData>) :RecyclerView.Adapter<RecyclerAdapter.ViewHolderItem>() {
    //listOf

    //１６）,rAdapter : RecyclerAdapter// RecyclerAdapterを受け取る
    inner class ViewHolderItem(v:View,rAdapter:RecyclerAdapter) :RecyclerView.ViewHolder(v) {
        val tvHolder : TextView = v.findViewById(R.id.tv)
        //追加
        val cbHolder:CheckBox = v.findViewById(R.id.checkBox)


        //１２）クリック処理(1行分の画面(view)が押されたら～)
//        init {
//            v.setOnClickListener {
//                val position:Int = adapterPosition
//                val listPosition = todoList[position]
//
//                //１４）アラートダイアログ
//                AlertDialog.Builder(v.context)
//                    .setTitle("${listPosition.myTodo}")
//                    .setMessage("本当に削除しますか")
//                    //１５）yesボタンを押した時の処理
//                    .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
//                        todoList.removeAt(position)
//                        //１６）表示を更新
//                        rAdapter.notifyItemRemoved(position)
//                    })
//                    .setNegativeButton("No",null)
//                    .show()
//
//                //１３）トーストでposition番目を表示してみる
//                //Toast.makeText(v.context,"$listPosition",Toast.LENGTH_SHORT).show()
//                //Toast.makeText(v.context,"${listPosition.myTodo}",Toast.LENGTH_SHORT).show()
//            }
//        }
        init {
            // Click listener for the entire row
            v.setOnClickListener {
                // Your existing code for showing the AlertDialog
                val position:Int = adapterPosition
                val listPosition = todoList[position]

                //１４）アラートダイアログ
                AlertDialog.Builder(v.context)
                    .setTitle("${listPosition.myTodo}")
                    .setMessage("本当に削除しますか")
                    //１５）yesボタンを押した時の処理
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                        todoList.removeAt(position)
                        //１６）表示を更新
                        rAdapter.notifyItemRemoved(position)
                    })
                    .setNegativeButton("No",null)
                    .show()
            }

            // Checkbox 押した時
            cbHolder.setOnCheckedChangeListener { _, isChecked ->
                val position: Int = adapterPosition
                val currentItem = todoList[position]

                currentItem.isChecked = isChecked
                // Handle the logic for the checkbox being checked or unchecked

                // Add logic to set a line on the text when the checkbox is checked
                if (isChecked) {
                    tvHolder.paintFlags = tvHolder.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvHolder.paintFlags = tvHolder.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }
    //６）一行だけのViewを生成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_layout,parent,false)
        return ViewHolderItem(itemXml,this) //１６）RecyclerAdapterを受け渡す
    }

    //７）position番目のデータをレイアウト(xml)に表示するようセット
    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
//        val currentItem = todoList[position] //何番目のリスト（アイテム）ですか
//        holder.tvHolder.text = currentItem.myTodo //そのリストの中の要素を指定して代入TV
//        holder.cbHolder //そのリストの中の要素を指定して代入TV
//        holder.cbHolder.isChecked = currentItem.isChecked
//        holder.cbHolder.setOnCheckedChangeListener { _, isChecked ->
//            currentItem.isChecked = isChecked
//            // Handle the logic for the checkbox being checked or unchecked
//        }
//        holder.itemView.setOnLongClickListener {
//            AlertDialog.Builder(holder.itemView.context)
//                .setTitle("Delete Item")
//                .setMessage("Are you sure you want to delete this item?")
//                .setPositiveButton("Yes") { _, _ ->
//                    todoList.removeAt(position)
//                    notifyDataSetChanged()
//                }
//                .setNegativeButton("No", null)
//                .show()
//            true
//        }
        val currentItem = todoList[position]
        holder.tvHolder.text = currentItem.myTodo
        holder.cbHolder.isChecked = currentItem.isChecked

        // Set the initial state of the text based on the checkbox
        if (currentItem.isChecked) {
            holder.tvHolder.paintFlags = holder.tvHolder.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.tvHolder.paintFlags = holder.tvHolder.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

    }

    //８）リストサイズ
    override fun getItemCount(): Int {
        return todoList.size
    }


}