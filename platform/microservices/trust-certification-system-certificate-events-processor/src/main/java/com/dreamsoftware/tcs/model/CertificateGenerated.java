package com.dreamsoftware.tcs.model;

import java.io.File;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateGenerated {

    private UUID id;
    private File file;
    private File image;

}
