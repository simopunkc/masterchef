package com.pasuh.masterchef.model.dao.dao_menu_inventory.floor2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.pasuh.masterchef.model.erd.MenuFood
import com.pasuh.masterchef.model.orm.MenuIngredientSingle
import com.pasuh.masterchef.model.orm.TotalCount
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDetailDao {
    @Query("SELECT A.*,C.name,C.quantity as stock FROM menuingredient AS A INNER JOIN ingredient AS C ON A.id_recipe=C.id WHERE A.id_menu = :id")
    fun getListIngredient(id: Int): Flow<List<MenuIngredientSingle>>

    @Query("SELECT COUNT(id) AS total FROM ingredient")
    fun getTotalRecipe(): Flow<TotalCount>

    @Delete
    suspend fun deleteMenu(menuFood: MenuFood)

    @Query("SELECT * FROM menufood WHERE id = :id LIMIT 1")
    fun getItem(id: Int): Flow<MenuFood>
}