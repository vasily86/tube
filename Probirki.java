/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checktubes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.firebirdsql.pool.*;

/**
 *
 * @author Vasily Glazunov
 */
public class Probirki {
    
    //private org.firebirdsql.pool.FBSimpleDataSource pool;
    
    private org.firebirdsql.pool.FBWrappingDataSource pool;
    
    public void initFireBase() throws Exception{       
        Class.forName("org.firebirdsql.jdbc.FBDriver");
                        
        //pool = new FBSimpleDataSource();        
        pool = new FBWrappingDataSource();        

        //pool.setDatabase("localhost/3050:D:/DB.FDB");
        pool.setDatabase("192.168.2.2/3050:E:/NMTWIN1251.GDB");
        //pool.setDatabase("192.168.0.1/3050:D:/DB.FDB");
        //pool.setDatabase("localhost/3050:C:/test_base/MOSK.FDB");
        pool.setUserName("SYSDBA");
        pool.setPassword("masterkey");    
        pool.setCharSet("CP1251");
    }
    
    
    public void init() throws Exception {
        initFireBase();
        
    }
    
    public ArrayList<InfoProbirka> getGoingProbirki(String data) throws Exception {
        ArrayList<InfoProbirka> list = new ArrayList<InfoProbirka>();
        String sql = "select examid, id_tube, count(*), t.title from probirka p " +
            "inner join tube t on (p.id_tube = t.tube_id) " +
            "where p.dates >= '" + data + "' and p.status = 'O'   " +
            //"and (p.id_prob between (select nomer from nomer_office)*1000000 and (select nomer from nomer_office)*1000000 + 999999) " +
            "group by examid, id_tube, t.title";
        
        try (   Connection con = pool.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);                 
        ) {
            while (rs.next()) {
                InfoProbirka info = new InfoProbirka(rs.getString("examid"), rs.getInt("id_tube"), rs.getInt("count"), rs.getString("title"));
                list.add(info);
            }            
        } catch (Exception e) {
            throw e;
        }
                        
        return list;
    }
    
    public ArrayList<InfoProbirka> getLaboratoryProbirki() throws Exception {
        ArrayList<InfoProbirka> list = new ArrayList<InfoProbirka>();
        String sql = "select examid, id_tube, count(*) from probirka p " +
            "where p.dates >= '22.04.2013' and p.status = 'L'  and " +
            "(p.examid between (select nomer from nomer_office)*1000000 and (select nomer from nomer_office)*1000000 + 999999) " +
            "group by examid, id_tube";
        
        try (   Connection con = pool.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);                 
        ) {
            while (rs.next()) {
                InfoProbirka info = new InfoProbirka(rs.getString("examid"), rs.getInt("id_tube"), rs.getInt("count"));               
                list.add(info);
            }            
        } catch (Exception e) {
            throw e;
        }
                       
        return list;
    }
    
    public boolean checkProbirka(InfoProbirka info) throws Exception {
        String id_tube;
        if ( info.id_tube == 1 || info.id_tube == 7 ) {
            id_tube = "1, 7";
        } else 
            if ( info.id_tube == 2 || info.id_tube == 3 ) {
                id_tube = "2, 3";
            } else {
                id_tube = Integer.toString( info.id_tube );
            }
        
        String sql = "select count(*) from probirka p " +
            "where p.examid = " + info.examid + " and p.id_tube in ( " + id_tube  + 
            ") and p.status = 'L'";
        
        //System.out.println(sql);
        
        try (   Connection con = pool.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);                 
        ) {
            return (rs.next() && rs.getInt("count") >= info.count);             
        }  catch (Exception e) {
            throw e;
        }     
    }
    
    public int countProbirka(InfoProbirka info) throws Exception{
        
        String id_tube;
        if ( info.id_tube == 1 || info.id_tube == 7 ) {
            id_tube = "1, 7";
        } else 
            if ( info.id_tube == 2 || info.id_tube == 3 ) {
                id_tube = "2, 3";
            } else {
                id_tube = Integer.toString( info.id_tube );
            }
               
        String sql = "select count(*) from probirka p " +
            "where p.examid = " + info.examid + " and p.id_tube in  (" + id_tube  + 
            ") and p.status = 'L'";
        
        //System.out.println(sql);
        
        try (   Connection con = pool.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);                 
        ) {
            rs.next();
            return rs.getInt("count");             
        }  catch (Exception e) {
            throw e;
        }     
    }
}
