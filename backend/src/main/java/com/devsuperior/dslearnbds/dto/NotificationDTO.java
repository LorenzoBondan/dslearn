package com.devsuperior.dslearnbds.dto;

import java.time.Instant;
import java.util.Objects;

import com.devsuperior.dslearnbds.entities.Notification;


public class NotificationDTO {


	private Long id;
	private String text;
	private Instant moment;
	private boolean read;
	private String route;
	private Long userId;
	
	public NotificationDTO() {}

	public NotificationDTO(Long id, String text, Instant moment, Boolean read, String route, Long userId) {
		this.id = id;
		this.text = text;
		this.moment = moment;
		this.read = read;
		this.route = route;
		this.userId = userId;
	}
	
	// CONVERTER ENTIDADE PARA DTO
	public NotificationDTO(Notification entity) {
		id = entity.getId();
		this.text = entity.getText();
		this.moment = entity.getMoment();
		this.read = entity.isRead();
		this.route = entity.getRoute();
		this.userId = entity.getUser().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public Boolean isRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationDTO other = (NotificationDTO) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
