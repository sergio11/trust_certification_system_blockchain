package com.dreamsoftware.tcs.persistence.bc.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CertificationAuthorityBcEntity {

    /**
     * Id
     */
    private String id;

    /**
     * Is Enabled
     */
    private Boolean isEnabled;

    /**
     * Is Exists
     */
    private Boolean isExist;
}
