package nl.novi.deepwrench42.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitRequestDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.entities.ToolKitEntity;
import nl.novi.deepwrench42.helpers.FileStorageHelper;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.ToolKitService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tool-kit")
public class ToolKitController {

    private final ToolKitService toolKitService;
    private final UrlHelper urlHelper;
    private final FileStorageHelper fileStorageHelper;

    public ToolKitController(ToolKitService toolKitService, UrlHelper urlHelper, FileStorageHelper fileStorageHelper) {
        this.toolKitService = toolKitService;
        this.urlHelper = urlHelper;
        this.fileStorageHelper = fileStorageHelper;
    }

    @GetMapping
    public ResponseEntity<List<ToolKitResponseDTO>> getAllToolKits() {
        List<ToolKitResponseDTO> toolKits = toolKitService.findAllToolKits();
        return ResponseEntity.ok(toolKits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolKitResponseDTO> getToolKitById(@PathVariable Long id) {
        ToolKitResponseDTO toolKit = toolKitService.findToolKitById(id);
        return new ResponseEntity<ToolKitResponseDTO>(toolKit, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ToolKitResponseDTO> createToolKit(@Valid @RequestBody ToolKitRequestDTO toolKitInput) {
        ToolKitResponseDTO newToolKit = toolKitService.createToolKit(toolKitInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newToolKit.getId())).body(newToolKit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToolKitResponseDTO> updateToolKit(@PathVariable Long id, @Valid @RequestBody ToolKitRequestDTO toolKitInput) {
        ToolKitResponseDTO updatedToolKit = toolKitService.updateToolKit(id, toolKitInput);
        return new ResponseEntity<>(updatedToolKit, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToolKit(@PathVariable Long id) {
        toolKitService.deleteToolKit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //picture services
    @PostMapping("/{id}/photo")
    public ResponseEntity<ToolKitResponseDTO> addPictureToTool(@PathVariable("id") Long id, @RequestBody MultipartFile file) throws IOException {
        String fileName = fileStorageHelper.storeFile(file);
        ToolKitResponseDTO tool = toolKitService.assignPictureToTool(fileName, id);

        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(tool.getId())).body(tool);
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getPictureOfTool(@PathVariable("id") Long id, HttpServletRequest request){
        Resource resource = toolKitService.getPictureFromTool(id);

        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            /*
            "application/octet-steam" is de generieke mime type voor byte data.
            Het is beter om een specifiekere mimetype te gebruiken, zoals "image/jpeg".
            Mimetype is nodig om de frontend te laten weten welke soort data het is.
            Met de juiste MimeType en Content-Disposition, kun je de plaatjes of PDFs die je upload
            zelfs in de browser weergeven.
             */
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }
}
