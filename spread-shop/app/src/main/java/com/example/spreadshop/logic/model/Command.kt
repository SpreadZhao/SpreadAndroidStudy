package com.example.spreadshop.logic.model

import android.util.Log

object Command {
    val SHOW_ALL = "showall"
    val GET_RECOMMAND = "getrecommand"
    val GET_ALL_CATEGORY = "getallcategory"
    fun getSearchGoods(query: String): String{
        return "search${query}"
    }
    fun getCategoryGoods(query: String): String{
        return "category${query}"
    }

    fun getImageByReflect(imageName: String): Int{
        var field: Class<*>
        var res = 0
        try {
            field = Class.forName("com.example.spreadshop.R\$drawable")
            res = field.getField(imageName).getInt(field)

        }catch (e: java.lang.Exception){
            e.printStackTrace()
            Log.d("SpreadShopTest", "getImageByReflect exception!")
        }

        return res
    }
}


/*
* class Manager{
* sdfsdf
*   private Manager manager;
*
*   private Manager(){
*       sdfasf
*   }
*
*   static public Manager getInstance(){
*       if(manager == null){
*           return new Manager();
*       } else{
*           return manager;
*       }
*   }
*
* }
* */

/*
* //  Manager a = new Manager();
*       Manager m = Manager.getInstance();
*       Manager x = Manager.getInstance();
* */