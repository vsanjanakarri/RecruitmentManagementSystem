package com.RMS.Repository;

import java.util.List;

	import javax.transaction.Transactional;

	import org.springframework.data.jpa.repository.Query;
	import org.springframework.data.repository.CrudRepository;
	import org.springframework.data.repository.query.Param;

	import com.RMS.Bean.LanguageMaster;

	@Transactional
	public interface LanguageRepository extends CrudRepository<LanguageMaster, Integer>{

		@Query("from LanguageMaster where deleted='N'")
		List<LanguageMaster> getAllLanguageList();
		
		@Query("from LanguageMaster where language_id = :language_id and deleted='N'")
		LanguageMaster getLanguageById(@Param("language_id") Integer language_id);
	}


