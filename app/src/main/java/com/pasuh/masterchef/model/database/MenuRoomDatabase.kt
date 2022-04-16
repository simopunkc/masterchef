package com.pasuh.masterchef.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor1.IngredientListDao
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor2.IngredientAddDao
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor2.IngredientDetailDao
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor3.RestockAddDao
import com.pasuh.masterchef.model.dao.dao_maximum_profit.floor1.ProfitListDao
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor1.MenuListDao
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor2.MenuAddDao
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor2.MenuDetailDao
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor3.MenuDropdownIngredientListDao
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor3.MenuIngredientDetailDao
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor3.MenuSellDao
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor4.MenuIngredientAddDao
import com.pasuh.masterchef.model.dao.dao_restock_history.floor1.RestockListDao
import com.pasuh.masterchef.model.dao.dao_restock_history.floor2.RestockDetailDao
import com.pasuh.masterchef.model.dao.dao_sales_transaction_history.floor1.SalesTransactionListDao
import com.pasuh.masterchef.model.dao.dao_sales_transaction_history.floor2.SalesTransactionDetailDao
import com.pasuh.masterchef.model.erd.*

@Database(entities = [MenuFood::class, Ingredient::class, SalesTransaction::class, MenuIngredient::class, Restock::class], version = 10, exportSchema = false)
abstract class MenuRoomDatabase : RoomDatabase() {
    abstract fun menuListDao(): MenuListDao
    abstract fun menuDetailDao(): MenuDetailDao
    abstract fun profitListDao(): ProfitListDao
    abstract fun menuIngredientAddDao(): MenuIngredientAddDao
    abstract fun menuAddDao(): MenuAddDao
    abstract fun dropdownListDao(): MenuDropdownIngredientListDao
    abstract fun menuIngredientDetailDao(): MenuIngredientDetailDao
    abstract fun menuSellDao(): MenuSellDao
    abstract fun ingredientAddDao(): IngredientAddDao
    abstract fun restockAddDao(): RestockAddDao
    abstract fun ingredientDetailDao(): IngredientDetailDao
    abstract fun ingredientListDao(): IngredientListDao
    abstract fun restockDetailDao(): RestockDetailDao
    abstract fun restockListDao(): RestockListDao
    abstract fun salesTransactionDetailDao(): SalesTransactionDetailDao
    abstract fun salesTransactionListDao(): SalesTransactionListDao

    companion object {
        @Volatile
        private var INSTANCE: MenuRoomDatabase? = null

        fun getDatabase(context: Context): MenuRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MenuRoomDatabase::class.java,
                    "masterchef_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}