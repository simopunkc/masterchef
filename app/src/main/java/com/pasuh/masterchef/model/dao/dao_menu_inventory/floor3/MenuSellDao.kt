package com.pasuh.masterchef.model.dao.dao_menu_inventory.floor3

import androidx.room.*
import com.pasuh.masterchef.model.erd.Ingredient
import com.pasuh.masterchef.model.erd.SalesTransaction
import com.pasuh.masterchef.model.orm.MenuIngredient
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuSellDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaksi(salesTransaction: SalesTransaction)

    @Query("SELECT B.id,B.name,B.price,group_concat(A.id_recipe) AS id_recipe,group_concat(A.recipe_quantity) AS quantity,group_concat(C.quantity) AS stock FROM menuingredient AS A INNER JOIN menufood AS B ON A.id_menu=B.id INNER JOIN ingredient AS C ON A.id_recipe=C.id WHERE A.id_menu = :id GROUP BY A.id_menu")
    fun getSingleIngredients(id: Int): Flow<MenuIngredient>

    @Transaction @Query("SELECT * FROM ingredient WHERE id IN (:list) LIMIT :length")
    fun getTransactionUpdatedRecipes(list: List<Int>, length: Int): Flow<List<Ingredient>>

    @Update
    suspend fun updateRecipe(ingredient: Ingredient)
    @Transaction
    suspend fun transactionUpdateRecipe(ingredient: Ingredient){
        updateRecipe(ingredient)
    }
}