package com.example.tma.skypeforbusiness.model;

import android.provider.Telephony;

import com.example.tma.skypeforbusiness.model.Me.MakeMeAvailable;
import com.example.tma.skypeforbusiness.model.Me.Photo;

import java.io.Serializable;

/**
 * Created by tmvien on 2/14/17.
 */
public class Links implements Serializable {
    private LinkContain self;
    private LinkContain next;
    private LinkContain resync;
    private LinkContain resume;


    private MakeMeAvailable makeMeAvailable;
    private Photo photo;
    private Photo contactPhoto;
    private LinkContain events;
    //contact
    private LinkContain presenceSubscriptions;
    private LinkContain subscribedContacts;
    private LinkContain contactPresence;
    private LinkContain contactNote;

    //    private PresenceSubscriptionMemberships presenceSubscriptionMemberships;
    private MyGroups myGroups;
//    private MyGroupMemberships;
//    private MyPrivacyRelationships myPrivacyRelationships;

//    private SkypeConversations conversations;
//    private StartMessaging startMessaging;
//    private StartOnlineMeeting startOnlineMeeting;
//    private JoinOnlineMetting joinOnlineMetting;

    public LinkContain getSelf() {
        return self;
    }

    public void setSelf(LinkContain self) {
        this.self = self;
    }

    public MakeMeAvailable getMakeMeAvailable() {
        return makeMeAvailable;
    }

    public void setMakeMeAvailable(MakeMeAvailable makeMeAvailable) {
        this.makeMeAvailable = makeMeAvailable;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    private MyContacts myContacts;

    public MyContacts getMyContacts() {
        return myContacts;
    }

    public void setMyContacts(MyContacts myContacts) {
        this.myContacts = myContacts;
    }

    public Photo getContactPhoto() {
        return contactPhoto;
    }

    public void setContactPhoto(Photo contactPhoto) {
        this.contactPhoto = contactPhoto;
    }

    public LinkContain getPresenceSubscriptions() {
        return presenceSubscriptions;
    }

    public void setPresenceSubscriptions(LinkContain presenceSubscriptions) {
        this.presenceSubscriptions = presenceSubscriptions;
    }

    public LinkContain getSubscribedContacts() {
        return subscribedContacts;
    }

    public void setSubscribedContacts(LinkContain subscribedContacts) {
        this.subscribedContacts = subscribedContacts;
    }

    public LinkContain getEvents() {
        return events;
    }

    public void setEvents(LinkContain events) {
        this.events = events;
    }

    public LinkContain getNext() {
        return next;
    }

    public void setNext(LinkContain next) {
        this.next = next;
    }

    public LinkContain getResync() {
        return resync;
    }

    public void setResync(LinkContain resync) {
        this.resync = resync;
    }

    public LinkContain getResume() {
        return resume;
    }

    public void setResume(LinkContain resume) {
        this.resume = resume;
    }

    public LinkContain getContactPresence() {
        return contactPresence;
    }

    public void setContactPresence(LinkContain contactPresence) {
        this.contactPresence = contactPresence;
    }

    public LinkContain getContactNote() {
        return contactNote;
    }

    public void setContactNote(LinkContain contactNote) {
        this.contactNote = contactNote;
    }
}
