package com.pasuh.masterchef.model.dao.dao_maximum_profit.floor1

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.pasuh.masterchef.model.orm.MenuIngredient
import com.pasuh.masterchef.model.orm.TotalCount
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfitListDao {
    @Transaction @Query("SELECT COUNT(id) AS total FROM (SELECT B.id FROM menuingredient AS A INNER JOIN menufood AS B ON A.id_menu=B.id INNER JOIN ingredient AS C ON A.id_recipe=C.id GROUP BY A.id_menu) AS item;")
    fun getTotalMenu(): Flow<TotalCount>

    @Query("SELECT B.id,B.name,B.price,group_concat(A.id_recipe) AS id_recipe,group_concat(A.recipe_quantity) AS quantity,group_concat(C.quantity) AS stock FROM menuingredient AS A INNER JOIN menufood AS B ON A.id_menu=B.id INNER JOIN ingredient AS C ON A.id_recipe=C.id GROUP BY A.id_menu")
    fun getListIngredients(): Flow<List<MenuIngredient>>
}
