package org.mentalizr.persistence.mongo.formData;

import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSOs;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormElementDataSO;

import java.util.ArrayList;
import java.util.List;

public class FormDataMerger {

    public static FormDataSO merge(FormDataSO formDataSO, FormDataSO formDataSOPreexisting) {

        checkPlausibility(formDataSO, formDataSOPreexisting);

        List<FormElementDataSO> formElementDataSOListUpdated
                = updatePreexistingElements(formDataSO, formDataSOPreexisting);

        List<FormElementDataSO> formElementDataSOListPost
                = takeOverNewElements(formDataSO, formDataSOPreexisting, formElementDataSOListUpdated);

        return new FormDataSO(
                formDataSO.getUserId(),
                formDataSO.getContentId(),
                formDataSO.isEditable(),
                formElementDataSOListPost
        );
    }

    private static void checkPlausibility(FormDataSO formDataSO, FormDataSO formDataSOPreexisting) {
        if (FormDataSOs.isExercise(formDataSO) || FormDataSOs.isExercise(formDataSOPreexisting)) {
            throw new RuntimeException("Plausibility failed on merging FormData documents: exercise can not be merged.");
        }
    }

    private static List<FormElementDataSO> updatePreexistingElements(FormDataSO formDataSO, FormDataSO formDataSOPreexisting) {

        List<FormElementDataSO> formElementDataSOListPreexisting = formDataSOPreexisting.getFormElementDataList();
        List<FormElementDataSO> formElementDataSOListPost = new ArrayList<>();

        for (FormElementDataSO formElementDataSOPreexisting : formElementDataSOListPreexisting) {

            String formElementId = formElementDataSOPreexisting.getFormElementId();
            boolean updateFormElementData = FormDataSOs.containsFormElementDataId(formDataSO, formElementId);

            if (updateFormElementData) {
                //noinspection OptionalGetWithoutIsPresent
                formElementDataSOListPost.add(FormDataSOs.getFormElementDataById(formDataSO, formElementId).get());
            } else {
                formElementDataSOListPost.add(formElementDataSOPreexisting);
            }
        }

        return formElementDataSOListPost;
    }

    private static List<FormElementDataSO> takeOverNewElements(
            FormDataSO formDataSO,
            FormDataSO formDataSOPreexisting,
            List<FormElementDataSO> formElementDataSOListPost) {

        List<FormElementDataSO> formElementDataSOListUpdate = formDataSO.getFormElementDataList();
        for (FormElementDataSO formElementDataSOUpdate : formElementDataSOListUpdate) {

            String formElementId = formElementDataSOUpdate.getFormElementId();

            boolean takeOver = !FormDataSOs.containsFormElementDataId(formDataSOPreexisting, formElementId);

            if (takeOver) {
                formElementDataSOListPost.add(formElementDataSOUpdate);
            }
        }

        return formElementDataSOListPost;
    }

}
