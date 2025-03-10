package fr.progilone.pgcn.domain.workflow;

public enum WorkflowStateKey {

	INITIALISATION_DOCUMENT(Values.INITIALISATION_DOCUMENT), GENERATION_BORDEREAU(Values.GENERATION_BORDEREAU),
	VALIDATION_CONSTAT_ETAT(Values.VALIDATION_CONSTAT_ETAT),
	VALIDATION_BORDEREAU_CONSTAT_ETAT(Values.VALIDATION_BORDEREAU_CONSTAT_ETAT),
	CONSTAT_ETAT_AVANT_NUMERISATION(Values.CONSTAT_ETAT_AVANT_NUMERISATION),
	NUMERISATION_EN_ATTENTE(Values.NUMERISATION_EN_ATTENTE),
	CONSTAT_ETAT_APRES_NUMERISATION(Values.CONSTAT_ETAT_APRES_NUMERISATION),
	LIVRAISON_DOCUMENT_EN_COURS(Values.LIVRAISON_DOCUMENT_EN_COURS),
	RELIVRAISON_DOCUMENT_EN_COURS(Values.RELIVRAISON_DOCUMENT_EN_COURS),
	CONTROLES_AUTOMATIQUES_EN_COURS(Values.CONTROLES_AUTOMATIQUES_EN_COURS),
	CONTROLE_QUALITE_EN_COURS(Values.CONTROLE_QUALITE_EN_COURS), PREREJET_DOCUMENT(Values.PREREJET_DOCUMENT),
	PREVALIDATION_DOCUMENT(Values.PREVALIDATION_DOCUMENT), VALIDATION_DOCUMENT(Values.VALIDATION_DOCUMENT),
	VALIDATION_NOTICES(Values.VALIDATION_NOTICES), RAPPORT_CONTROLES(Values.RAPPORT_CONTROLES),
	ARCHIVAGE_DOCUMENT(Values.ARCHIVAGE_DOCUMENT), DIFFUSION_DOCUMENT(Values.DIFFUSION_DOCUMENT),
	DIFFUSION_DOCUMENT_OMEKA(Values.DIFFUSION_DOCUMENT_OMEKA),
	DIFFUSION_DOCUMENT_DIGITAL_LIBRARY(Values.DIFFUSION_DOCUMENT_DIGITAL_LIBRARY),
	DIFFUSION_DOCUMENT_LOCALE(Values.DIFFUSION_DOCUMENT_LOCALE),
	/* EXPORT_DOCUMENT, */
	CLOTURE_DOCUMENT(Values.CLOTURE_DOCUMENT);

	private WorkflowStateKey(final String value) {
		if (!name().equals(value)) {
			throw new IllegalArgumentException("Incorrect use of WorkflowStateKey");
		}
	}

	public static class Values {

		public static final String INITIALISATION_DOCUMENT = "INITIALISATION_DOCUMENT";

		public static final String GENERATION_BORDEREAU = "GENERATION_BORDEREAU";

		public static final String VALIDATION_CONSTAT_ETAT = "VALIDATION_CONSTAT_ETAT";

		public static final String VALIDATION_BORDEREAU_CONSTAT_ETAT = "VALIDATION_BORDEREAU_CONSTAT_ETAT";

		public static final String CONSTAT_ETAT_AVANT_NUMERISATION = "CONSTAT_ETAT_AVANT_NUMERISATION";

		public static final String NUMERISATION_EN_ATTENTE = "NUMERISATION_EN_ATTENTE";

		public static final String CONSTAT_ETAT_APRES_NUMERISATION = "CONSTAT_ETAT_APRES_NUMERISATION";

		public static final String LIVRAISON_DOCUMENT_EN_COURS = "LIVRAISON_DOCUMENT_EN_COURS";

		public static final String RELIVRAISON_DOCUMENT_EN_COURS = "RELIVRAISON_DOCUMENT_EN_COURS";

		public static final String CONTROLES_AUTOMATIQUES_EN_COURS = "CONTROLES_AUTOMATIQUES_EN_COURS";

		public static final String CONTROLE_QUALITE_EN_COURS = "CONTROLE_QUALITE_EN_COURS";

		public static final String PREREJET_DOCUMENT = "PREREJET_DOCUMENT";

		public static final String PREVALIDATION_DOCUMENT = "PREVALIDATION_DOCUMENT";

		public static final String VALIDATION_DOCUMENT = "VALIDATION_DOCUMENT";

		public static final String VALIDATION_NOTICES = "VALIDATION_NOTICES";

		public static final String RAPPORT_CONTROLES = "RAPPORT_CONTROLES";

		public static final String ARCHIVAGE_DOCUMENT = "ARCHIVAGE_DOCUMENT";

		public static final String DIFFUSION_DOCUMENT = "DIFFUSION_DOCUMENT";

		public static final String DIFFUSION_DOCUMENT_OMEKA = "DIFFUSION_DOCUMENT_OMEKA";

		public static final String DIFFUSION_DOCUMENT_LOCALE = "DIFFUSION_DOCUMENT_LOCALE";

		public static final String DIFFUSION_DOCUMENT_DIGITAL_LIBRARY = "DIFFUSION_DOCUMENT_DIGITAL_LIBRARY";

		public static final String CLOTURE_DOCUMENT = "CLOTURE_DOCUMENT";

	}

}
