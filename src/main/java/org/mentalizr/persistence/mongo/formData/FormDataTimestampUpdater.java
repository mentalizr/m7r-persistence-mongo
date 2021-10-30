package org.mentalizr.persistence.mongo.formData;

import org.mentalizr.commons.Dates;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSOs;

import java.util.List;

public class FormDataTimestampUpdater {

    public static void markExerciseAsSeenByTherapist(FormDataSO formDataSO) {
        if (FormDataSOs.isExercise(formDataSO) && !formDataSO.getExercise().isSeenByTherapist()) {
            formDataSO.getExercise().setSeenByTherapist(true);
            formDataSO.getExercise().setSeenByTherapistTimestamp(Dates.currentTimestampAsISO());
            FormDataDAO.createOrUpdate(formDataSO);
        }
    }

    public static void markExerciseAsSeenByTherapist(List<FormDataSO> formDataSOList) {
        formDataSOList.forEach(FormDataTimestampUpdater::markExerciseAsSeenByTherapist);
    }

    public static void markFeedbackAsSeenByPatient(FormDataSO formDataSO) {
        if (FormDataSOs.hasFeedback(formDataSO) && !formDataSO.getFeedback().isSeenByPatient()) {
            formDataSO.getFeedback().setSeenByPatient(true);
            formDataSO.getFeedback().setSeenByPatientTimestamp(Dates.currentTimestampAsISO());
            FormDataDAO.createOrUpdate(formDataSO);
        }
    }

}
