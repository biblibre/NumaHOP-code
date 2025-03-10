package fr.progilone.pgcn.domain.document;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import fr.progilone.pgcn.domain.AbstractDomainObject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe représentant un objet d'export IA
 */
@Entity
@Table(name = ArchiveItem.TABLE_NAME)
@JsonSubTypes({ @JsonSubTypes.Type(name = "doc_archive_item", value = ArchiveItem.class) })
public class ArchiveItem extends AbstractDomainObject {

	public static final String TABLE_NAME = "doc_archive_item";

	@OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final Set<ArchiveCollection> collections = new LinkedHashSet<>();

	@OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final Set<ArchiveSubject> subjects = new LinkedHashSet<>();

	@OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final Set<ArchiveHeader> headers = new LinkedHashSet<>();

	@OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final Set<ArchiveCoverage> coverages = new LinkedHashSet<>();

	@OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final Set<ArchiveContributor> contributors = new LinkedHashSet<>();

	@OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final Set<ArchiveCreator> creators = new LinkedHashSet<>();

	@OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final Set<ArchiveLanguage> languages = new LinkedHashSet<>();

	@Column(name = "archive_identifier")
	private String archiveIdentifier;

	@Column(name = "credits")
	private String credits;

	@Column(name = "date")
	private String date;

	@Column(name = "description")
	private String description;

	@Column(name = "license_url")
	private String licenseUrl;

	@Column(name = "mediatype")
	private String mediatype;

	@Column(name = "customMediatype")
	private String customMediatype;

	@Column(name = "notes")
	private String notes;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "rights")
	private String rights;

	@Column(name = "title")
	private String title;

	@Column(name = "type")
	private String type;

	@Column(name = "source")
	private String source;

	/**
	 * Unité documentaire rattachée
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doc_unit")
	private DocUnit docUnit;

	public DocUnit getDocUnit() {
		return docUnit;
	}

	public void setDocUnit(final DocUnit docUnit) {
		this.docUnit = docUnit;
	}

	public Set<ArchiveCollection> getCollections() {
		return collections;
	}

	public Set<ArchiveSubject> getSubjects() {
		return subjects;
	}

	public Set<ArchiveHeader> getHeaders() {
		return headers;
	}

	public Set<ArchiveCoverage> getCoverages() {
		return coverages;
	}

	public Set<ArchiveContributor> getContributors() {
		return contributors;
	}

	public Set<ArchiveCreator> getCreators() {
		return creators;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(final String credits) {
		this.credits = credits;
	}

	public String getDate() {
		return date;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Set<ArchiveLanguage> getLanguages() {
		return languages;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(final String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	public String getMediatype() {
		return mediatype;
	}

	public void setMediatype(final String mediatype) {
		this.mediatype = mediatype;
	}

	public String getCustomMediatype() {
		return customMediatype;
	}

	public void setCustomMediatype(final String customMediatype) {
		this.customMediatype = customMediatype;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(final String notes) {
		this.notes = notes;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(final String publisher) {
		this.publisher = publisher;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(final String rights) {
		this.rights = rights;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getArchiveIdentifier() {
		return archiveIdentifier;
	}

	public void setArchiveIdentifier(final String archiveIdentifier) {
		this.archiveIdentifier = archiveIdentifier;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

}
