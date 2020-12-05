package com.skilldistillery.doggiemeetup.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Double lat;

	private Double lng;

	@Column(name = "point_time")
	private LocalDateTime pointTime;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route routeId;

	public User getUser() {
		return user;
	}

	public void setUserId(User userId) {
		this.user = userId;
	}

	public Route getRouteId() {
		return routeId;
	}

	public void setRouteId(Route routeId) {
		this.routeId = routeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public LocalDateTime getPointTime() {
		return pointTime;
	}

	public void setPointTime(LocalDateTime pointTime) {
		this.pointTime = pointTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", lat=" + lat + ", lng=" + lng + ", pointTime=" + pointTime + ", user=" + user
				+ ", routeId=" + routeId + "]";
	}

}
