package nl.novi.deepwrench42.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.helpers.FileStorageHelper;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.ToolService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tool")
public class ToolController {

    private final ToolService toolService;
    private final UrlHelper urlHelper;
    private final FileStorageHelper fileStorageHelper;

    public ToolController(ToolService toolService, UrlHelper urlHelper, FileStorageHelper fileStorageHelper) {
        this.toolService = toolService;
        this.urlHelper = urlHelper;
        this.fileStorageHelper = fileStorageHelper;
    }

    @GetMapping
    public ResponseEntity<List<ToolResponseDTO>> getAllTools() {
        List<ToolResponseDTO> tools = toolService.findAllTools();
        return ResponseEntity.ok(tools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolResponseDTO> getToolById(@PathVariable Long id) {
        ToolResponseDTO tool = toolService.findToolById(id);
        return new ResponseEntity<ToolResponseDTO>(tool, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ToolResponseDTO> createTool(@Valid @RequestBody ToolRequestDTO toolInput) {
        ToolResponseDTO newTool = toolService.createTool(toolInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newTool.getId())).body(newTool);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToolResponseDTO> updateTool(@PathVariable Long id, @Valid @RequestBody ToolRequestDTO toolInput) {
        ToolResponseDTO updatedTool = toolService.updateTool(id, toolInput);
        return new ResponseEntity<>(updatedTool, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //picture services
    @PostMapping("/{id}/picture")
    public ResponseEntity<ToolResponseDTO> addPictureToTool(@PathVariable("id") Long id, @RequestBody MultipartFile file) throws IOException {
        String fileName = fileStorageHelper.storeFile(file);
        ToolResponseDTO tool = toolService.assignPictureToTool(fileName, id);

        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(tool.getId())).body(tool);
    }

    @GetMapping("/{id}/picture")
    public ResponseEntity<Resource> getPictureOfTool(@PathVariable("id") Long id, HttpServletRequest request){
        Resource resource = toolService.getPictureFromTool(id);

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
