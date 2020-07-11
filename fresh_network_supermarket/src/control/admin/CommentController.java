package control.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.GoodsComment;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CommentController {
    @FXML
    private TableColumn<GoodsComment, Timestamp> col_date;

    @FXML
    private TableColumn<GoodsComment, Integer> col_id;

    @FXML
    private TableColumn<GoodsComment, String> col_detail;

    @FXML
    private TableColumn<GoodsComment, Integer> col_level;

    @FXML
    private TableView<GoodsComment> view_comment;

    @FXML
    private Text text_level;

    public ObservableList<GoodsComment> comment = FXCollections.observableArrayList();

    @FXML
    void initialize() {
    }

    public void initData(int id){
        comment=load(id);
        col_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        col_detail.setCellValueFactory(new PropertyValueFactory<>("comment_detail"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("comment_date"));
        col_level.setCellValueFactory(new PropertyValueFactory<>("comment_level"));
        view_comment.getItems().setAll(comment);
    }

    private ObservableList<GoodsComment> load(int id) {
        ObservableList<GoodsComment> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods_comment where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                GoodsComment r = new GoodsComment();
                r.setGoods_id(rs.getInt(1));
                r.setUser_id(rs.getInt(2));
                r.setComment_detail(rs.getString(3));
                r.setComment_date(rs.getTimestamp(4));
                r.setComment_level(rs.getInt(5));
                result.add(r);
            }
            sql = "select avg (comment_level) from goods_comment where goods_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            rs= pst.executeQuery();
            if (rs.next()){
                text_level.setText("平均星级："+rs.getDouble(1)+"星级");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return result;
    }

}
