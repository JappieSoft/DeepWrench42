package nl.novi.deepwrench42.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tool")
@Tag(name = "Tool Management")
public class ToolController {

    private final ToolService toolService;
    private final UrlHelper urlHelper;
    private final FileStorageHelper fileStorageHelper;

    public ToolController(ToolService toolService, UrlHelper urlHelper, FileStorageHelper fileStorageHelper) {
        this.toolService = toolService;
        this.urlHelper = urlHelper;
        this.fileStorageHelper = fileStorageHelper;
    }

    @Operation(
            description = "Get details of all tools",
            summary = "Get details of all tools"
    )
    @GetMapping
    public ResponseEntity<List<ToolResponseDTO>> getAllTools() {
        List<ToolResponseDTO> tools = toolService.findAllTools();
        return ResponseEntity.ok(tools);
    }

    @Operation(
            description = "Get details of a specific tool id",
            summary = "Get details of a specific tool"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ToolResponseDTO> getToolById(@PathVariable Long id) {
        ToolResponseDTO tool = toolService.findToolById(id);
        return new ResponseEntity<ToolResponseDTO>(tool, HttpStatus.OK);
    }

    @Operation(
            description = "Create a new tool, admin & leads only",
            summary = "Create a new tool"
    )
    @PostMapping
    public ResponseEntity<ToolResponseDTO> createTool(@Valid @RequestBody ToolRequestDTO toolInput) {
        ToolResponseDTO newTool = toolService.createTool(toolInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newTool.getId())).body(newTool);
    }

    @Operation(
            description = "Update a specific tool id, admin & leads only",
            summary = "Update a specific tool"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ToolResponseDTO> updateTool(@PathVariable Long id, @Valid @RequestBody ToolRequestDTO toolInput) {
        ToolResponseDTO updatedTool = toolService.updateTool(id, toolInput);
        return new ResponseEntity<>(updatedTool, HttpStatus.OK);
    }

    @Operation(
            description = "Delete a specific tool with id, admin only",
            summary = "Delete a specific tool"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //picture services
    @Operation(
            description = "Upload a new tool picture for a specific tool id, admin & leads only, but please use Postman for this function",
            summary = "Upload a new tool picture"
    )
    @PostMapping("/{id}/picture")
    public ResponseEntity<ToolResponseDTO> addPictureToTool(@PathVariable("id") Long id, @RequestBody MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("No file uploaded");
        }

        String toolItemId = toolService.findToolById(id).getItemId();
        String existingFileName = toolService.findToolById(id).getPictureFileName();
        String fileName = fileStorageHelper.storeFile(toolItemId, existingFileName, file);
        ToolResponseDTO tool = toolService.assignPictureToTool(fileName, id);

        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(tool.getId())).body(tool);
    }

    @Operation(
            description = "Get a tool picture for a specific tool id, please use Postman for this function",
            summary = "Get a tool picture"
    )
    @GetMapping("/{id}/picture")
    public ResponseEntity<Resource> getPictureOfTool(@PathVariable("id") Long id, HttpServletRequest request) {
        Resource resource = toolService.getPictureFromTool(id);
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
            description = "Get all tools with a specific tool status",
            summary = "Get all tools with a specific status"
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ToolResponseDTO>> getToolByStatus(@PathVariable String status) {
        List<ToolResponseDTO> tools = toolService.findToolsPerStatus(status);
        return ResponseEntity.ok(tools);
    }
}
