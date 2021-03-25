package br.gov.sp.etecsebrae.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.etecsebrae.entity.LoginEntity;

public interface LoginRepository extends JpaRepository<LoginEntity, Long> {

	default Optional<LoginEntity> findByLogin(String login) {
		return this.findAll().stream().filter(entity -> login.equalsIgnoreCase(entity.getLogin())).findAny();
	}

}
