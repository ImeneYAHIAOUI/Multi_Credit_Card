package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Survey;

import java.lang.reflect.Member;
import java.util.List;

public interface MailSender {
    void sendPromotions(List<MemberAccount> members, String mailToSend);
    void sendSurvey(List<MemberAccount> members, Survey survey);
    void sendVFPReminders(List<MemberAccount> members);
}
