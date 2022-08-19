/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.MessageAccount;
import fpt.aptech.KHR.Entities.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author LÊ HỮU TÂM
 */
public interface MesageAccountRepository extends JpaRepository<MessageAccount, Integer> {
    @Query("SELECT m FROM MessageAccount m WHERE m.idReceive = :idReceive AND m.mailSend = :mailSend")
    List<MessageAccount> findMesage(@PathVariable("idReceive") String idReceive,@PathVariable("mailSend") String mailSend);
    @Query("SELECT m FROM MessageAccount m WHERE m.idReceive = :idReceive")
    List<MessageAccount> findMessageStore(@PathVariable("idReceive") String idReceive);
    @Query("SELECT m FROM MessageAccount m WHERE m.mailSend = :mailSend")
    List<MessageAccount> findMessageAccount(@PathVariable("mailSend") String mailSend);
    @Query("SELECT m FROM MessageAccount m WHERE (m.idReceive = :idReceive AND m.mailSend = :mailSend) OR (m.idReceive = :mailSend AND m.mailSend = :idReceive)")
    List<MessageAccount> findMesageSento(@PathVariable("idReceive") String idReceive,@PathVariable("mailSend") String mailSend);
    
}
