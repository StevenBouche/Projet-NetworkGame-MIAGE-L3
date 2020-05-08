package coco.controller.handle;

import coco.controller.PlayerData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import network.message.obj.PlayerMoneyInfo;

import java.util.List;

public class HandlerPlayerDataTable {

    private TableView<PlayerData> tableView;
    public List<PlayerData> listPlayerData;

    public HandlerPlayerDataTable(TableView<PlayerData> tableView, List<PlayerData> listPlayerData){
        this.tableView = tableView;
        this.listPlayerData = listPlayerData;
        initTable();
    }

    public void updateDataPlayer(PlayerMoneyInfo var) {
        PlayerData p = getPlayerData(var.id);
        if(p!=null){
            p.cashRound = var.CagnotteRound;
            p.cashTotal = 0; //todo
            updateTable(this.listPlayerData);
        }
    }

   private void initTable(){
        tableView.setEditable(false);

       TableColumn<PlayerData,String> idColumn = new TableColumn<>("ID");
       idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PlayerData,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("namePlayer"));

        TableColumn<PlayerData,Integer> cashColumn = new TableColumn<>("CashRound");
        cashColumn.setCellValueFactory(new PropertyValueFactory<>("cashRound"));

       TableColumn<PlayerData,Integer> cashColumnTot = new TableColumn<>("CashTotal");
       cashColumnTot.setCellValueFactory(new PropertyValueFactory<>("cashTotal"));

       tableView.getColumns().addAll(nameColumn,cashColumn,cashColumnTot);
     //   tableView.getColumns().add(cashColumn);
     //   tableView.getColumns().add(cashColumnTot);
       // updateTable(listPlayerData);
       updateTable(listPlayerData);

    }

    private void updateTable(List<PlayerData> list){
        tableView.getItems().clear();
        tableView.getItems().addAll(listPlayerData);
     /*   ObservableList<PlayerData> listO = FXCollections.observableArrayList(list);
        tableView.setItems(listO);
        tableView.refresh();*/
    }

    public PlayerData getPlayerData(String id){
        for(PlayerData p : listPlayerData){
            if(p.id.equals(id)) {
                return p;
            }
        }
        return null;
    }

    private void updateNamePlayer(int id, String nP){
        listPlayerData.get(id).namePlayer = nP;
    }

   /* private void addCashPlayer(int id, int newCash){
        listPlayerData.get(id).cashPlayer += newCash;
    }

    private void resetCashPlayer(int id, int newCash){
        listPlayerData.get(id).cashPlayer = newCash;
    }

*/

}
