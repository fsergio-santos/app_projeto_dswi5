package com.crm.domain.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pessoa.class)
public abstract class Pessoa_ {

	public static volatile SingularAttribute<Pessoa, PessoaFisica> pessoaFisica;
	public static volatile SingularAttribute<Pessoa, Boolean> registro_deletado;
	public static volatile SingularAttribute<Pessoa, String> nome;
	public static volatile SingularAttribute<Pessoa, Long> id;
	public static volatile ListAttribute<Pessoa, Telefone> telefones;
	public static volatile SingularAttribute<Pessoa, String> email;

	public static final String PESSOA_FISICA = "pessoaFisica";
	public static final String REGISTRO_DELETADO = "registro_deletado";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String TELEFONES = "telefones";
	public static final String EMAIL = "email";

}

