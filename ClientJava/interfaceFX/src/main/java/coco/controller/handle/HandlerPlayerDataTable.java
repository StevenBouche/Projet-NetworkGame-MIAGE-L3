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
            p.cashPlayerRound = var.CagnotteRound;
        //    p.cashPlayerTotal = var.CagnotteTotal; todo
            updateTable(this.listPlayerData);
        }
    }

   private void initTable(){
        tableView.setEditable(false);
        TableColumn<PlayerData,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("namePlayer"));
        TableColumn<PlayerData,Integer> cashColumn = new TableColumn<>("Cash");
        cashColumn.setCellValueFactory(new PropertyValueFactory<>("cashPlayerRound"));
       TableColumn<PlayerData,Integer> cashColumnTot = new TableColumn<>("Cash");
       cashColumn.setCellValueFactory(new PropertyValueFactory<>("cashPlayerTotal"));
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(cashColumn);
       tableView.getColumns().add(cashColumnTot);
        updateTable(listPlayerData);
    }

    private void updateTable(List<PlayerData> list){
        Platform.runLater(() -> {
            ObservableList<PlayerData> listO = FXCollections.observableArrayList(list);
            tableView.setItems(listO);
        });
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
