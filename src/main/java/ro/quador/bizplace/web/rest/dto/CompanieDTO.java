package ro.quador.bizplace.web.rest.dto;

import ro.quador.bizplace.domain.Companie;

public class CompanieDTO {

	private Long id;
	private String cui;
	private String nume;
	private String adresa;
	private String telefon;
	private String email;
	private String website;
	private Long judet;
	private Long activitate;

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public CompanieDTO() {
		super();
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CompanieDTO(String cui, String nume, String adresa, String telefon, String email, String website, Long judet, Long activitate) {
		super();
		this.cui = cui;
		this.nume = nume;
		this.adresa = adresa;
		this.telefon = telefon;
		this.email = email;
		this.website = website;
		this.judet = judet;
		this.activitate = activitate;
	}

	public CompanieDTO(Companie companie) {
		super();
		this.id = companie.getId();
		this.cui = companie.getCui();
		this.nume = companie.getNume();
		this.adresa = companie.getAdresa();
		this.telefon = companie.getTelefon();
		this.email = companie.getEmail();
		this.website = companie.getWebsite();
		this.judet = companie.getJudet().getId();
		this.activitate = companie.getActivitate().getId();
	}



	@Override
	public String toString() {
		return "CompanieDTO [id=" + id + ", cui=" + cui + ", nume=" + nume + ", adresa=" + adresa + ", telefon=" + telefon + ", email=" + email
				+ ", website=" + website + ", judet=" + judet + ", activitate=" + activitate + "]";
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Long getJudet() {
		return judet;
	}

	public void setJudet(Long judet) {
		this.judet = judet;
	}

	public Long getActivitate() {
		return activitate;
	}

	public void setActivitate(Long activitate) {
		this.activitate = activitate;
	}

}
