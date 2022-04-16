package com.pasuh.masterchef.model.dao.dao_menu_inventory.floor3

import androidx.room.Dao
import androidx.room.Query
import com.pasuh.masterchef.model.erd.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDropdownIngredientListDao {
    @Query("SELECT C.* FROM ingredient AS C WHERE NOT EXISTS (SELECT id_recipe FROM menuingredient AS A WHERE A.id_menu = :id AND id_recipe=C.id)")
    fun getDropdownIngredient(id: Int): Flow<List<Ingredient>>
}
