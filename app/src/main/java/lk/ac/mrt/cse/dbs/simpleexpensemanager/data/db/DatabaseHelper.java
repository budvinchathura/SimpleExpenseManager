package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class DatabaseHelper extends SQLiteOpenHelper {

    public  static final String DB_NAME = "expense_manager";
    public  static final String TABLE_1 = "account";
    public  static final String TABLE_2 = "transaction";



    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String ddl_q = "create table "+TABLE_1+" (accountNo TEXT(50) PRIMARY KEY,bankName TEXT(50),accountHolderName TEXT(50),balance REAL); ";
        ddl_q+=" create table "+TABLE_2+" (accountNo TEXT(50), expenseType TEXT(20),amount REAL);";
        sqLiteDatabase.execSQL(ddl_q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String delete_q = ("DROP TABLE IF EXISTS "+TABLE_1+"; ");
        delete_q+=" DROP TABLE IF EXISTS "+TABLE_2+";";
        sqLiteDatabase.execSQL((delete_q));
        onCreate(sqLiteDatabase);
    }

    public boolean insertAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNo",account.getAccountNo());
        contentValues.put("bankName",account.getBankName());
        contentValues.put("accountHolderName",account.getAccountHolderName());
        contentValues.put("balance",account.getBalance());
        long result = db.insert(TABLE_1,null,contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Account> getAllAccounts(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_1,null);
        ArrayList<Account> accountList=new ArrayList<>();
        if(res.getCount()==0){
            return accountList;
        }else{

            while(res.moveToNext()){
                String accountNo = res.getString(0);
                String bankName = res.getString(1);
                String accountHolderName = res.getString(2);
                double balance = res.getDouble(3);
                accountList.add(new Account(accountNo,bankName,accountHolderName,balance));
            }
            return accountList;
        }
    }

    public boolean deleteAccount(String accountNo){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_1,"accountNo = "+accountNo,null) > 0;

    }
}
