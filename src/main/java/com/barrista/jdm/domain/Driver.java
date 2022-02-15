package com.barrista.jdm.domain;


import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class Message
{
		@Id
		@GeneratedValue(strategy= GenerationType.AUTO)
		private Integer id;

		private String text;
		private String tags;

		public Integer getId()
		{
				return id;
		}

		public void setId(Integer id)
		{
				this.id = id;
		}

		public String getText()
		{
				return text;
		}

		public void setText(String text)
		{
				this.text = text;
		}

		public String getTags()
		{
				return tags;
		}

		public void setTags(String tags)
		{
				this.tags = tags;
		}
}
