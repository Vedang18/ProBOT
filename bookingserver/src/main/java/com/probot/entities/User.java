package com.probot.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Entity
@Table(name = "users", uniqueConstraints={@UniqueConstraint(columnNames = {"channelId" , "userId"})})
public class User implements Serializable
{

    private static final long serialVersionUID = 5875883710607618313L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;
    @Version
    private Long version;

    private String channelId;
    private String userId;
    private String username;
    private String password;
    private String address;
    

    public User()
    {

    }

    public User(String channelId, String username, String password)
    {
        this.channelId = channelId;
        this.username = username;
        this.password = password;
    }

    public String getChannelId()
    {
        return channelId;
    }

    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getUid()
    {
        return uid;
    }

    public void setUid( int uid )
    {
        this.uid = uid;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public Long getVersion()
    {
        return version;
    }

    public void setVersion( Long version )
    {
        this.version = version;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

}
