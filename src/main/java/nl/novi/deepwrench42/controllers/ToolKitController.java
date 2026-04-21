package nl.novi.deepwrench42.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitRequestDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitResponseDTO;
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
import java.util.Objects;

@RestController
@RequestMapping("/tool-kit")
@Tag(name = "Tool Kit Management")
public class ToolKitController {

    private final ToolKitService toolKitService;
    private final UrlHelper urlHelper;
    private final FileStorageHelper fileStorageHelper;

    public ToolKitController(ToolKitService toolKitService, UrlHelper urlHelper, FileStorageHelper fileStorageHelper) {
        this.toolKitService = toolKitService;
        this.urlHelper = urlHelper;
        this.fileStorageHelper = fileStorageHelper;
    }

    @Operation(
            description = "Get details of all tool kits",
            summary = "Get details of all tool kits"
    )
    @GetMapping
    public ResponseEntity<List<ToolKitResponseDTO>> getAllToolKits() {
        List<ToolKitResponseDTO> toolKits = toolKitService.findAllToolKits();
        return ResponseEntity.ok(toolKits);
    }

    @Operation(
            description = "Get details of a specific tool kit id",
            summary = "Get details of a specific tool kit"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ToolKitResponseDTO> getToolKitById(@PathVariable Long id) {
        ToolKitResponseDTO toolKit = toolKitService.findToolKitById(id);
        return new ResponseEntity<ToolKitResponseDTO>(toolKit, HttpStatus.OK);
    }

    @Operation(
            description = "Create a new tool kit, admin & leads only",
            summary = "Create a new tool kit"
    )
    @PostMapping
    public ResponseEntity<ToolKitResponseDTO> createToolKit(@Valid @RequestBody ToolKitRequestDTO toolKitInput) {
        ToolKitResponseDTO newToolKit = toolKitService.createToolKit(toolKitInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newToolKit.getId())).body(newToolKit);
    }

    @Operation(
            description = "Update a specific tool kit id, admin & leads only",
            summary = "Update a specific tool kit"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ToolKitResponseDTO> updateToolKit(@PathVariable Long id, @Valid @RequestBody ToolKitRequestDTO toolKitInput) {
        ToolKitResponseDTO updatedToolKit = toolKitService.updateToolKit(id, toolKitInput);
        return new ResponseEntity<>(updatedToolKit, HttpStatus.OK);
    }

    @Operation(
            description = "Delete a specific tool kit with id, admin only",
            summary = "Delete a specific tool kit"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToolKit(@PathVariable Long id) {
        toolKitService.deleteToolKit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //picture services
    @Operation(
            description = "Upload a new tool kit picture for a specific tool id, admin & leads only, but please use Postman for this function",
            summary = "Upload a new tool kit picture"
    )
    @PostMapping("/{id}/picture")
    public ResponseEntity<ToolKitResponseDTO> addPictureToToolKit(@PathVariable("id") Long id, @RequestBody MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("No file uploaded");
        }

        String toolKitItemId = toolKitService.findToolKitById(id).getItemId();
        String existingFileName = toolKitService.findToolKitById(id).getPictureFileName();
        String fileName = fileStorageHelper.storeFile(toolKitItemId, existingFileName, file);
        ToolKitResponseDTO tool = toolKitService.assignPictureToTool(fileName, id);

        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(tool.getId())).body(tool);
    }

    @Operation(
            description = "Get a tool kit picture for a specific tool id, please use Postman for this function",
            summary = "Get a tool kit picture"
    )
    @GetMapping("/{id}/picture")
    public ResponseEntity<Resource> getPictureOfToolKit(@PathVariable("id") Long id, HttpServletRequest request) {
        Resource resource = toolKitService.getPictureFromTool(id);
        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = fileStorageHelper.getMimeType(Objects.requireNonNull(resource.getFilename()));
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }

    @Operation(
            description = "Get all tool kits with a specific tool status",
            summary = "Get all tool kits with a specific status"
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ToolKitResponseDTO>> getToolKitByStatus(@PathVariable String status) {
        List<ToolKitResponseDTO> toolKits = toolKitService.findToolKitsPerStatus(status);
        return ResponseEntity.ok(toolKits);
    }
}
