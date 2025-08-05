//package ra.project_jvw_service.controller;
//
//@RestController
//@RequestMapping("/api/round_criteria")
//public class RoundCriterionController {
//    @Autowired
//    private RoundCriterionService roundCriterionService;
//
//    @GetMapping
//    public ResponseEntity<List<RoundCriterionResponse>> getAll(@RequestParam Long roundId) {
//        return ResponseEntity.ok(roundCriterionService.getAll(roundId));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<RoundCriterionResponse> getById(@PathVariable Integer id) {
//        return ResponseEntity.ok(roundCriterionService.getById(id));
//    }
//
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<RoundCriterionResponse> create(@RequestBody @Valid RoundCriterionRequest dto) {
//        return new ResponseEntity<>(roundCriterionService.create(dto), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<RoundCriterionResponse> update(@PathVariable Integer id, @RequestBody @Valid RoundCriterionRequest dto) {
//        return ResponseEntity.ok(roundCriterionService.update(id, dto));
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Void> delete(@PathVariable Integer id) {
//        roundCriterionService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}