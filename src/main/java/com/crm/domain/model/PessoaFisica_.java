package com.crm.domain.model;

import com.crm.domain.model.enumerate.Sexo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PessoaFisica.class)
public abstract class PessoaFisica_ {

	public static volatile SingularAttribute<PessoaFisica, Pessoa> pessoa;
	public static volatile SingularAttribute<PessoaFisica, String> cpf;
	public static volatile SingularAttribute<PessoaFisica, String> nomePai;
	public static volatile SingularAttribute<PessoaFisica, Long> id;
	public static volatile SingularAttribute<PessoaFisica, Date> dataNascimento;
	public static volatile SingularAttribute<PessoaFisica, Sexo> sexo;
	public static volatile SingularAttribute<PessoaFisica, String> nomeMae;

	public static final String PESSOA = "pessoa";
	public static final String CPF = "cpf";
	public static final String NOME_PAI = "nomePai";
	public static final String ID = "id";
	public static final String DATA_NASCIMENTO = "dataNascimento";
	public static final String SEXO = "sexo";
	public static final String NOME_MAE = "nomeMae";

}

