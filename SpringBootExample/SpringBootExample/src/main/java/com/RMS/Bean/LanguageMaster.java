package com.RMS.Bean;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.Table;

	@Entity
	@Table(name = "language_master")
	public class LanguageMaster { 

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name="language_id",columnDefinition="int")
		private Integer language_id;
		
		@Column(name = "language")
		private String language;
		
		@Column(name = "deleted")
		private String deleted; // Y=Yes, N=No  Default : N

		public Integer getLanguage_id() {
			return language_id;
		}

		public void setLanguage_id(Integer language_id) {
			this.language_id = language_id;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getDeleted() {
			return deleted;
		}

		public void setDeleted(String deleted) {
			this.deleted = deleted;
		}

		

}
